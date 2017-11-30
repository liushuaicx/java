package com.kaishengit.crm.controller;

import com.kaishengit.crm.auth.ShiroUtils;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.provider.MD5;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录,登出
 * @author 刘帅
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String login () {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("是否认证?" + subject.isAuthenticated());
        System.out.println("是否记住我?"+subject.isRemembered());
        if (subject.isAuthenticated()) {
            //如果已经验证,回到登录页面,认为是切换账户
            subject.logout();
        }
        if (!subject.isAuthenticated()&&subject.isRemembered()) {
            return "redirect:/home";
        }
        return "login";
    }

    /**
     * 表单登录
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/")
    public String login(String mobile,String password,boolean rememberMe, RedirectAttributes redirectAttributes,
                        HttpServletRequest request) {

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(mobile,new Md5Hash(password).toString(),rememberMe);
            subject.login(usernamePasswordToken);

            //转到登陆前url
            String url = "/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if (savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }

            return "redirect:"+url;
        }catch (AuthenticationException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/home")
    public String home () {

        return "home";
    }

    @GetMapping("/exit")
    public String exit(HttpSession session,RedirectAttributes redirectAttributes) {

        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","已安全退出");
        return "redirect:/";
    }

}
