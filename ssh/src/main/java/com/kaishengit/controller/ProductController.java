package com.kaishengit.controller;

import com.kaishengit.pojo.Product;
import com.kaishengit.service.ProductService;
import com.kaishengit.util.Page;
import com.kaishengit.util.RequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String list(Model model, HttpServletRequest request,@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo) {

        //获得请求参数类型
        List<RequestQuery> requestQueryList = RequestQuery.getRequestQueryList(request);
        Page<Product> productList = productService.findByQueryList(pageNo,requestQueryList);
        model.addAttribute("page", productList);
        return "product/list";
    }

    @GetMapping("/new")
    public String newProduct() {
        return "product/new";
    }

    @PostMapping("/new")
    public String newProduct(Product product) {
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String deleteById(@PathVariable(required = false) Integer id) {
        productService.delete(id);
        return "redirect:/product";
    }
    @GetMapping("/update/{id:\\d+}")
    public String update(@PathVariable(required = false) Integer id,Model model) {

        Product product = productService.findById(id);

        model.addAttribute("product",product);
        return "product/update";
    }

    @PostMapping("/update/{id:\\d+}")
    public String update(Product product) {
        productService.save(product);
        return "redirect:/product";
    }

}
