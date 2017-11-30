package com.kaishengit.controller;

import com.kaishengit.pojo.Product;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String list(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
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
