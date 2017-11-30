package com.kaishengit.crm.controller;

import com.kaishengit.crm.auth.ShiroUtils;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/my")
    public String sale(HttpSession session, Model model) {
        User user = getUser(session);

        //查询所有客户的销售机会
        List<SaleChance> saleChanceList = saleService.findAllSaleWithCustomerName(user.getId());

        model.addAttribute("saleChanceList",saleChanceList);


        return "sale/my";
    }



    @GetMapping("/new")
    public String newSaleChange(HttpSession session,Model model) {

        User user = getUser(session);
        //获得所有客户
        List<Customer> customerList = customerService.findAll(user.getId());

        model.addAttribute("customerList",customerList);

        return "sale/new";
    }

    @PostMapping("/new")
    public String newSaleChange(HttpSession session,SaleChance saleChance, RedirectAttributes redirectAttributes) {
        User user =getUser(session);
        saleService.newChange(user,saleChance);
        redirectAttributes.addFlashAttribute("message","添加成功");
        return "redirect:/sale/my";
    }

    @GetMapping("/detail/{id:\\d+}")
    public String detail(@PathVariable Integer id,HttpSession session,Model model) {

        User user =getUser(session);
        //通过id查询机会归属
        SaleChance saleChance = checkSaleChance(id, user);

        //查询客户资料
        Customer customer = customerService.findCustomerDetailById(saleChance.getCustId());
        //查询progressList
        List<String> progressList = saleService.findProgressList();
        //查询saleChanceRecordList
        List<SaleChanceRecord> saleChanceRecordList = saleService.findSaleChanceRecordList(id);

        model.addAttribute("saleChance",saleChance);
        model.addAttribute("customer",customer);
        model.addAttribute("progressList",progressList);
        model.addAttribute("saleChanceRecordList",saleChanceRecordList);

        return "sale/detail";

    }



    @PostMapping("/my/{id:\\d+}/progress/update")
    public String updateProgress(@PathVariable Integer id, String progress,
                                 HttpSession session,RedirectAttributes redirectAttributes) {

        checkSaleChance(id,getUser(session));

        //更新当前进度
        saleService.updateProgress(id,progress);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/sale/detail/"+ id;
    }

    @PostMapping("/my/new/record")
    public String newRecord(String content,Integer saleId,HttpSession session) {

        checkSaleChance(saleId,getUser(session));

        saleService.newRecord(content,saleId);
        return "redirect:/sale/detail/"+saleId;
    }
    @GetMapping("/del/{id:\\d+}")
    public String delSaleChance(@PathVariable Integer id,HttpSession session,
                                RedirectAttributes redirectAttributes) {

        checkSaleChance(id,getUser(session));
        saleService.delSaleChance(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/sale/my";
    }


    /**
     * 检验是否存在和机会归属
     * @param id
     * @param user
     * @return
     */
    private SaleChance checkSaleChance(@PathVariable Integer id, User user) {
        SaleChance saleChance = saleService.findSaleById(id);
        if (saleChance == null) {
            throw new NotFoundException("信息不存在");
        }
        if (!saleChance.getUserId().equals(user.getId())) {
            throw new ForbiddenException("权限不足");
        }
        return saleChance;
    }


    private User getUser(HttpSession session) {
        //获得登录对象
        return ShiroUtils.getCurrentUser();
    }
}
