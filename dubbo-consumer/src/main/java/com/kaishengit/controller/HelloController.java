package com.kaishengit.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kaishengit.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushuai
 */
@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @GetMapping("/")
    public String sayHello() {
        helloService.sayHello("tom");
        return "hello,world";
    }

}
