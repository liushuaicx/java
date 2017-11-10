package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private UserService userService;

    @GetMapping("/my")
    public String customerList(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNO,
                           Integer userId, Model model,HttpSession session) {
        User user = (User) session.getAttribute("curr_user");
        userId = user.getId();

        PageInfo<Customer> customerPageInfo = userService.findAllCustomer(pageNO,userId);
        model.addAttribute("customerPageInfo",customerPageInfo);
        return "customer";

    }


    @GetMapping("/add")
    public String addCustomer (Integer userId, Model model,HttpSession session) {

        User user = (User) session.getAttribute("curr_user");
        userId = user.getId();

        List<Customer> customerList = userService.findAll(userId);
        model.addAttribute("customerList",customerList);
        return "addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(Integer userId,Customer customer, RedirectAttributes redirectAttributes,
                              HttpSession session) {

        User user = (User) session.getAttribute("curr_user");
        userId = user.getId();

        try {
            userService.addCustomer(userId,customer);
            redirectAttributes.addFlashAttribute("message","添加成功");
            return "redirect:/customer/my";
        }catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/customer/add";
        }

    }

    @GetMapping("/detail")
    public String customerDetail(Integer id,Model model) {

        model.addAttribute("customer",userService.findCustomerDetailById(id));
        return "customerDetail";
    }

}
