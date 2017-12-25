package com.kaishengit.tms.system.api;

import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.system.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liushuai
 */
@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String login() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        if (!subject.isAuthenticated() && subject.isRemembered()) {
            return "redirect:/home";
        }
        return "account/login";
    }

    @PostMapping("/")
    public String login(String accountMobile, String accountPassword, boolean rememberMe,
                        RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(accountMobile,new Md5Hash(accountPassword).toString(), rememberMe);
            subject.login(usernamePasswordToken);

            String url = "/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if (savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }
            //登陆成功前记录日志
            String ip = request.getRemoteAddr();
            accountService.saveLoginLog((Account) subject.getPrincipal(), ip);

            return "redirect:" + url;
        }catch (AuthenticationException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/";
        }
    }
    @GetMapping("/home")
    public String home() {
        return "account/home";
    }
    @GetMapping("/exit")
    public String exit(RedirectAttributes redirectAttributes) {
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","已安全退出");
        return "redirect:/";
    }
}
