package com.kaishengit.tms.store.api;

import com.kaishengit.tms.entity.Customer;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.store.auth.ShiroUtil;
import com.kaishengit.tms.store.files.QiNiuFileStore;
import com.kaishengit.tms.system.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author liushuai
 */
@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;
    @GetMapping("/")
    public String home() {
        return "store/home";
    }

    @PostMapping("/detail")
    public String detail(Integer ticketNum, Model model) {
        Ticket ticket = storeService.findByTicketNum(ticketNum);
        model.addAttribute("ticket",ticket);
        return "store/home";
    }

    @GetMapping("/handle")
    public String handle() {
        return "store/handle";
    }

    @PostMapping("/handle")
    public String handle(Customer customer, Integer ticketNum, MultipartFile file,
                         MultipartFile file1, MultipartFile file2,
                         String ticketOrderType,
                         RedirectAttributes redirectAttributes) {

        String newFileName;
        String newFileName1;
        String newFileName2;

        try {
            //获得文件输入流
            InputStream inputStream = file.getInputStream();
            InputStream inputStream1 = file1.getInputStream();
            InputStream inputStream2 = file2.getInputStream();
            //获得初始文件名
            String fileName = file.getOriginalFilename();
            String fileName1 = file1.getOriginalFilename();
            String fileName2 = file2.getOriginalFilename();

            QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
            newFileName = qiNiuFileStore.saveFile(inputStream, fileName);
            newFileName1 = qiNiuFileStore.saveFile(inputStream1, fileName1);
            newFileName2 = qiNiuFileStore.saveFile(inputStream2, fileName2);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "身份证上传异常");
            return "redirect:/store/handle";
        }
        //获得当前登陆对应的Store
        StoreAccount storeAccount = ShiroUtil.getCurrentAccount();

        customer.setCustomerIdCardPhoto(newFileName);
        customer.setCustomerIdCardPhotoBack(newFileName1);
        customer.setCustomerPhoto(newFileName2);
        //添加顾客和年票对应的信息
        try {
            storeService.addCustomer(customer, ticketNum, storeAccount.getId(), ticketOrderType);
            redirectAttributes.addFlashAttribute("message", "添加成功");
            return "redirect:/store/";
        } catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/store/handle";
        }
    }

    @GetMapping("/payment")
    public String payment() {
        return "store/payment";
    }

    @PostMapping("/payment")
    public String payment(Integer ticketNum, Integer money, RedirectAttributes redirectAttributes) {

        Date date = storeService.payment(ticketNum, money);
        redirectAttributes.addFlashAttribute("message", "到期时间为: " + date);
        return "redirect:/store/";
    }

    @GetMapping("/guashi")
    public String reportLoss() {
        return "store/guashi";
    }

    @PostMapping("/guashi")
    public String reportLoss(String idCardNum, RedirectAttributes redirectAttributes) {

        try {
            storeService.reportLoss(idCardNum);
            redirectAttributes.addFlashAttribute("message", "挂失成功");
            return "redirect:/store/";
        } catch (ServiceException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/store/guashi";
        }
    }

    @GetMapping("/restore")
    public String restore() {
        return "store/restore";
    }

    @PostMapping("/restore")
    public String restore(Integer ticketNum, String idCardNum, RedirectAttributes redirectAttributes) {

        try {
            storeService.restore(ticketNum, idCardNum);
            redirectAttributes.addFlashAttribute("message", "解挂成功");
            return "redirect:/store/";
        } catch (ServiceException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/store/restore";
        }

    }

    @GetMapping("/replace")
    public String replace() {
        return "store/replace";
    }

    @PostMapping("/replace")
    public String replace(String idCardNum, Integer newTicketNum, RedirectAttributes redirectAttributes) {

        StoreAccount storeAccount = ShiroUtil.getCurrentAccount();
        try {
            storeService.replace(idCardNum, newTicketNum, storeAccount.getId());
            redirectAttributes.addFlashAttribute("message", "补卡成功");
            return "redirect:/store/";
        } catch (ServiceException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/store/replace";
        }

    }

}
