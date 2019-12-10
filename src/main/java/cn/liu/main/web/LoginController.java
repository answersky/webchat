package cn.liu.main.web;

import cn.liu.main.utils.DESTest;
import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.service.IUserInfoService;
import com.alibaba.druid.support.json.JSONUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by liuf on 2017/1/7.
 */
@Controller
public class LoginController {
    @Resource
    private IUserInfoService userInfoService;

    private final static String MIYAO = "answer12345678910lfxmsky";

    @RequestMapping("login")
    public String loginPage(HttpServletRequest request) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            return "redirect:/index";
        }
        return "login/login";
    }

    @RequestMapping("/sign_up")
    public String sign_up() {
        return "login/register";
    }

    @RequestMapping("/")
    public String tologinPage(HttpServletRequest request) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @RequestMapping("/toLogin")
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, String username, String password) {
        Map<String, Object> result = new LinkedHashMap<>();
        UserInfo user = userInfoService.findUserByUsername(username);
        if (user == null) {
            result.put("message", "用户不存在");
            result.put("status", "2");
            return result;
        }
        String pass = DESTest.encrypt(password, MIYAO);
        UsernamePasswordToken token = new UsernamePasswordToken(username, pass);
        // shiro登陆验证
        try {
            SecurityUtils.getSubject().login(token);
            //设置登录超时时长 2小时  毫秒
            SecurityUtils.getSubject().getSession().setTimeout(7200000);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("userId", user.getId());
            result.put("message", "登录成功");
            result.put("status", "0");
        } catch (UnknownAccountException ex) {
            result.put("message", "用户不存在");
            result.put("status", "2");
        } catch (IncorrectCredentialsException ex) {
            result.put("message", "密码错误");
            result.put("status", "2");
        } catch (AuthenticationException ex) {
            result.put("message", "系统异常");
            result.put("status", "2");
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("message", "系统异常");
            result.put("status", "2");
        }
        return result;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("userId");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            String userInfo = request.getParameter("user");
            Map<String, String> map = (Map<String, String>) JSONUtils.parse(userInfo);
            String username = map.get("username");
            String password = map.get("password");
            String pass = DESTest.encrypt(password, MIYAO);
            ;
            String phone = map.get("phone");
            //校验手机号
            String regexp = "^1[3456789]\\d{9}$";
            if (!Pattern.matches(regexp, phone)) {
                result.put("message", "手机号格式不正确");
                result.put("status", "2");
                return result;
            }
            String nickname = map.get("nickname");
            nickname = URLDecoder.decode(nickname, "utf-8");
            String age = map.get("age");
            String re = "^[1-9]\\d*$";
            if (!Pattern.matches(re, age)) {
                result.put("message", "请输入正常的年龄值");
                result.put("status", "2");
                return result;
            }
            String address = map.get("address");
            address = URLDecoder.decode(address, "utf-8");
            UserInfo user = new UserInfo();
            user.setUsername(username);
            user.setPassword(pass);
            user.setNickname(nickname);
            user.setAge(Integer.parseInt(age));
            user.setPhone(phone);
            user.setAddress(address);
            user.setCreate_time(new Date());
            userInfoService.saveUser(user);
            result.put("message", "注册成功");
            result.put("status", "0");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "系统异常");
            result.put("status", "2");
        }
        return result;
    }
}
