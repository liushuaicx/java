package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleService;
import com.kaishengit.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/charts")
public class ChartsController extends BaseController{

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SaleService saleService;

    @GetMapping
    public String show() {
        return "charts/show";
    }


    @GetMapping("/lever.json")
    @ResponseBody
    public AjaxResult customerLeverData(HttpSession session) {

        User user = getCurrentUser(session);
        List<Map<String,Object>> result = customerService.findCustomerCountByLever(user.getId());
        return AjaxResult.successWithData(result);
    }
    @GetMapping("/progress.json")
    @ResponseBody
    public AjaxResult saleChangeProgress(HttpSession session) {

        User user = getCurrentUser(session);
        List<Map<String,Object>> result = saleService.findSaleChanceCountByProgress(user.getId());

        return AjaxResult.successWithData(result);
    }

    @GetMapping("/count.json")
    @ResponseBody
    public AjaxResult customerCountByCreateTime(HttpSession session) {

        User user = getCurrentUser(session);
        List<Map<String,Object>> result = customerService.findCustomerCountByCreateTime(user.getId());
        return AjaxResult.successWithData(result);
    }


}
