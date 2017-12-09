package com.kaishengit;

import com.kaishengit.service.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-consumer.xml");
        ProductService productService = (ProductService) context.getBean("consumer");
        List<String> productNames = productService.findAllProductName();
        for (String name : productNames) {
            System.out.println(name);
        }

    }
}
