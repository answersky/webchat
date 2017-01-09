package cn.liu.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuf on 2017/1/7.
 */
@Controller
public class LoginController {
    @RequestMapping("login")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/")
    public String tologinPage(){
        return "redirect:/login";
    }

    @RequestMapping("/toLogin")
    public String toIndex(HttpServletRequest request,String username, String password){
        request.getSession().setAttribute("username",username);
        return "redirect:/index";
    }
}
