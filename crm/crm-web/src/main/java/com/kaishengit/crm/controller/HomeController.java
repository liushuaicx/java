package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "login";
    }

    /**
     * 表单登录
     * @param user
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping("/")
    public String login(User user , RedirectAttributes redirectAttributes,HttpSession session) {

        try {
            User res = userService.findByNameAndPassword(user.getUserName(),user.getPassword());
            session.setAttribute("curr_user",res);
            return "redirect:/home";
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

        session.invalidate();
        redirectAttributes.addFlashAttribute("message","已安全退出");
        return "redirect:/";
    }

}
