package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.auth.ShiroUtils;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @GetMapping("/my")
    public String customerList(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNO,
                               Model model,HttpSession session) {

        Integer userId = getUserId(session);

        PageInfo<Customer> customerPageInfo = customerService.findAllCustomer(pageNO,userId);
        model.addAttribute("customerPageInfo",customerPageInfo);
        return "customer/my";

    }


    @GetMapping("/add")
    public String addCustomer ( Model model,HttpSession session) {

        Integer userId = getUserId(session);

        model.addAttribute("tradeList",customerService.findAllTrade());
        model.addAttribute("sourceList",customerService.findAllSource());
        return "customer/addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(Customer customer, RedirectAttributes redirectAttributes,
                              HttpSession session) {

        Integer userId = getUserId(session);

        try {
            customerService.addCustomer(userId,customer);
            redirectAttributes.addFlashAttribute("message","添加成功");
            return "redirect:/customer/my";
        }catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/customer/add";
        }

    }

    @GetMapping("/detail/{id:\\d+}")
    public String customerDetail(@PathVariable Integer id, Model model,HttpSession session) {

        Customer customer = checkCustomer(id, session);
        List<SaleChanceRecord> saleChanceRecordList =  userService.findSaleChanceRecordList(id);
        List<Task> taskList = customerService.findTaskList(id);
        model.addAttribute("taskList",taskList);
        model.addAttribute("saleChanceRecordList",saleChanceRecordList);
        model.addAttribute("userList",userService.findAllUser());
        model.addAttribute("customer",customer);
        return "customer/customerDetail";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String deleteCustomer(@PathVariable Integer id,HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        checkCustomer(id,session);
        System.out.println(id);
        customerService.deleteCustomerById(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/customer/my";

    }
    @GetMapping("/edit/{id:\\d+}")
    public String editCustomer(@PathVariable Integer id,HttpSession session,Model model) {

        Customer customer = checkCustomer(id,session);
        model.addAttribute("customer",customer);
        model.addAttribute("tradeList",customerService.findAllTrade());
        model.addAttribute("sourceList",customerService.findAllSource());

        return "customer/editCustomer";

    }

    @PostMapping("/edit/{id:\\d+}")
    public String editCustomer(Customer customer,HttpSession session ,
                               RedirectAttributes redirectAttributes) {

        checkCustomer(customer.getId(),session);
        customerService.editCustomer(customer);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/customer/detail/"+customer.getId();
    }

    @GetMapping("/my/{customerId:\\d+}/to/{toUserId:\\d+}")
    public String changeUser(@PathVariable Integer customerId,
                             @PathVariable Integer toUserId,HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Customer customer = checkCustomer(customerId,session);

        customerService.changeUser(customer,toUserId);
        redirectAttributes.addFlashAttribute("message","转交成功");
        return "redirect:/customer/my";

    }
    @GetMapping("/my/{customerId:\\d+}/public")
    public String pushPublic(@PathVariable Integer customerId,
                             HttpSession session,RedirectAttributes redirectAttributes) {

        Customer customer = checkCustomer(customerId,session);
        customerService.pushPublic(customer);
        return "redirect:/customer/my";
    }

    /**
     * 数据输出为csv类型
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/my/export.csv")
    public void exportCsv(HttpSession session, HttpServletResponse response)
            throws IOException {

        User user = (User) session.getAttribute("curr_user");

        //设置mimeType
        response.setContentType("text/csv;charset=GBK");
        String fileName = new String("我的客户.csv".getBytes("UTF-8"),"ISO8859-1");
        //设置处理方式
        response.setHeader("Content-Disposition","attachment; fileName=\""+fileName+"\"");
        OutputStream outputStream = response.getOutputStream();
        customerService.exportCsvToOutputStream(user,outputStream);
    }

    /**
     * 数据输出为Xls类型
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/my/export.xls")
    public void exportXls(HttpSession session,HttpServletResponse response)
            throws IOException {

        User user = (User) session.getAttribute("curr_user");

        //设置mimeType
        response.setContentType("application/vnd.ms-excel");
        String fileName = new String("我的客户.xls".getBytes("UTF-8"),"ISO8859-1");
        //设置处理方式
        response.setHeader("Content-Disposition","attachment; fileName=\""+fileName+"\"");
        OutputStream outputStream = response.getOutputStream();
        customerService.exportXlsToOutputStream(user,outputStream);
    }










    /**
     * 获得公海客户列表
     * @param pageNO
     * @param model
     * @return
     */

    @GetMapping("/public")
    public String customerPublic(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNO,Model model) {

        PageInfo<Customer> pageInfo = customerService.findAllCustomerPublic(pageNO);
        model.addAttribute("pageInfo",pageInfo);
        return "customer/public";
    }




    /**
     * 在session里获得userId
     * @param session
     * @return
     */
    private static int getUserId(HttpSession session) {
        User user = ShiroUtils.getCurrentUser();
        return user.getId();
    }

    /**
     * 检查是否存在和客户归属
     * @param id 客户id
     * @param session 当前登录session
     * @return customer对象
     */
    private Customer checkCustomer(@PathVariable Integer id, HttpSession session) {
        Customer customer = customerService.findCustomerDetailById(id);

        if (customer == null) {
            //404
            throw new NotFoundException("客户不存在");
        }
        if (!customer.getUserId().equals(getUserId(session))) {
            //403
            throw  new ForbiddenException("没有权限查看该用户");
        }
        return customer;
    }
}
