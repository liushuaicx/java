package com.kaishengit.controller;

import com.kaishengit.entity.Product;
import com.kaishengit.service.ProductService;
import com.kaishengit.util.AjaxResult;
import com.kaishengit.util.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("productList",productService.findAll());
        return "home";
    }
    @GetMapping("/product/new")
    public String newProduct() {
        return "new";
    }

    /**
     * 添加商品
     * @param product
     * @param sTime
     * @param eTime
     * @param image
     * @return
     */
    @PostMapping("/product/new")
    public String newProduct(Product product, String sTime, String eTime, MultipartFile image) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime startTime = DateTime.parse(sTime,dateTimeFormatter);
        DateTime endTime = DateTime.parse(eTime,dateTimeFormatter);

        product.setStartTime(startTime.toDate());
        product.setEndTime(endTime.toDate());

        System.out.println(product.getEndTime());

        if (image.isEmpty()) {
            productService.saveProduct(product,null);
        }else {
            try {
                productService.saveProduct(product,image.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    /**
     * 商品详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/product/{id:\\d+}")
    public String detail(@PathVariable Integer id,Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "product";
    }

    @GetMapping("/product/secKill/{id:\\d+}")
    @ResponseBody
    public AjaxResult secKill(@PathVariable Integer id) {
        try {
            productService.secKill(id);
            return AjaxResult.success();
        }catch (ServiceException ex) {
            return AjaxResult.error(ex.getMessage());
        }

    }

}
