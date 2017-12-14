package com.kaishengit.tms.system.api;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.Account;
import com.kaishengit.tms.exception.ServiceException;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 账号验证
 * @author liushuai
 */
@Controller
public class AccountController {

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
        return "login";
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
        return "home";
    }
    @GetMapping("/exit")
    public String exit(RedirectAttributes redirectAttributes) {
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message","已安全退出");
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newAccount() {

        return "new";
    }
    @PostMapping("/new")
    public String newAccount(Account account, Integer[] roleId,RedirectAttributes redirectAttributes) {

        //保存账户
        try {
            accountService.saveAccount(account, roleId);
            redirectAttributes.addFlashAttribute("message","添加成功");
        }catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/list";
    }

    @GetMapping("/delete/{id:\\+}")
    public String deleteAccount(@PathVariable(name = "id") Integer accountId,RedirectAttributes redirectAttributes) {
        try {
            accountService.deleteAccount(accountId);
            redirectAttributes.addFlashAttribute("message","删除成功");
        }catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/list";
    }
    @GetMapping("/update/{id:\\d+}")
    public String updateAccount(@PathVariable Integer id, Model model) {

        //根据id查询account
        model.addAttribute("account",accountService.findAccountById(id));
        //查询账户对应的 权限
        model.addAttribute("RoleList",accountService.findRoleByAccountId(id));
        return "detail";
    }

    @PostMapping("/update/{id:\\d+}")
    public String updateAccount(Account account,Integer[] roleIdList) {

        accountService.updateAccount(account,roleIdList);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String AccountList(@PathVariable(required = false,name = "p",value = "1") Integer pageNo,
                              Model model) {

        PageInfo<Account> accountPageInfo = accountService.findAccountListByPage(pageNo);
        model.addAttribute("accountPageInfo",accountPageInfo);
        return "list";
    }

}
