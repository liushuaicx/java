package com.kaishengit.tms.store.api;

import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.system.service.StoreService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 售票点登陆
 *
 * @author liushuai
 */
@Controller
@RequestMapping("/store")
public class StoreLoginController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/")
    public String login() {

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        if (subject.isAuthenticated() & subject.isRemembered()) {
            return "/store/home";
        }
        return "store/login";
    }

    @PostMapping("/")
    public String login(String storeAccount, String storePassword, Boolean rememberMe,
                        HttpServletRequest request, RedirectAttributes redirectAttributes) {

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(storeAccount,
                    new Md5Hash(storePassword).toString(), rememberMe);

            subject.login(usernamePasswordToken);
            String url = "/store/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if (savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }
            String ip = request.getRemoteAddr();
            storeService.saveLoginLog(ip, (StoreAccount) subject.getPrincipal());
            return "redirect:" + url;
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "账号或密码错误");
            return "redirect:/store/";
        }

    }

    @GetMapping("/exit")
    public String exit(RedirectAttributes redirectAttributes) {
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","您已安全退出");
        return "redirect:/store/login";
    }

    @GetMapping("/home")
    public String home() {
        return "store/home";
    }
}
