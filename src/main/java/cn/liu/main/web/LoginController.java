package cn.liu.main.web;

import cn.liu.webChat.domain.UserInfo;
import cn.liu.webChat.mybatis_dao.IUserInfoDao;
import cn.liu.webChat.service.IUserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuf on 2017/1/7.
 */
@Controller
public class LoginController {
    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("login")
    public String loginPage(HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (userInfo != null) {
            return "redirect:/index";
        }
        return "login";
    }

    @RequestMapping("/")
    public String tologinPage(HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if (userInfo != null) {
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @RequestMapping("/toLogin")
    public String toIndex(HttpServletRequest request,String username, String password){
        UserInfo userInfo = userInfoService.findUserByUsername(username);
        if (userInfo != null) {
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("userId", userInfo.getId());
            request.getSession().setAttribute("userInfo", userInfo);
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("username", "");
        request.getSession().setAttribute("userId", "");
        request.getSession().setAttribute("userInfo", "");
        return "redirect:/login";
    }
}
