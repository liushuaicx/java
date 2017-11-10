package com.kaishengit.controller;

import com.kaishengit.entity.Dept;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login () {
        return "login";
    }
    @PostMapping("/login")
    public String login(User user , RedirectAttributes redirectAttributes) {

        User res = userService.findByNameAndPassword(user.getUserName(),user.getPassword());
        System.out.println(user.getUserName()+"===================>>>>>"+user.getPassword());
         if (res != null) {
             return "redirect:/list";
         }else {
             redirectAttributes.addFlashAttribute("message","账户名或密码错误");
             return "redirect:/login";
         }
    }
    @GetMapping("/list")
    public String list(@RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNO, Model model) {
        List<Dept> deptList = userService.finAllDept();
        model.addAttribute("deptList",deptList);
        model.addAttribute("pageInfo",userService.finAllUser(pageNO));
        return "/list";
    }
    @GetMapping("/addUser")
    public String addUser() {
        return "/addUser";
    }
    @PostMapping("/addUser")
    public String addUser(User user,RedirectAttributes redirectAttributes) {

        userService.addUser(user);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/list";
    }
    @PostMapping("/addDept")
    public String addDept(Dept dept,RedirectAttributes redirectAttributes) {

        return "redirect:/list";
    }
}
