package com.kaishengit.service;

import com.kaishengit.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-hibernate.xml")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Test
    public void findAll() throws Exception {

        List<Product> productList = productService.findAll();

        for (Product product : productList) {
            System.out.println(product.getProductName());
        }
    }

}