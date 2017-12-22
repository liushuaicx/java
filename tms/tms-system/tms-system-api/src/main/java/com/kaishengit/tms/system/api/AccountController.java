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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 账号验证
 * @author liushuai
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/new")
    public String newAccount(Model model) {
        model.addAttribute("roleList",accountService.findAllRole());
        return "account/new";
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
        return "redirect:/account/list";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String deleteAccount(@PathVariable(name = "id") Integer accountId,RedirectAttributes redirectAttributes) {
        try {
            accountService.deleteAccount(accountId);
            redirectAttributes.addFlashAttribute("message","删除成功");
        }catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/account/list";
    }
    @GetMapping("/update/{id:\\d+}")
    public String updateAccount(@PathVariable Integer id, Model model) {

        //根据id查询account
        model.addAttribute("account",accountService.findAccountById(id));
        //查询账户对应的 权限
        model.addAttribute("role",accountService.findRoleByAccountId(id));
        model.addAttribute("roleList",accountService.findAllRole());
        return "account/edit";
    }

    @PostMapping("/update/{id:\\d+}")
    public String updateAccount(Account account,Integer[] roleId) {
        accountService.updateAccount(account,roleId);
        return "redirect:/account/list";
    }

    @GetMapping("/list")
    public String accountList(@RequestParam(required = false,defaultValue ="1",name = "p") Integer pageNo,
                              Model model) {

        PageInfo<Account> accountPageInfo = accountService.findAccountListByPage(pageNo);
        model.addAttribute("accountPageInfo",accountPageInfo);
        return "account/list";
    }

}
