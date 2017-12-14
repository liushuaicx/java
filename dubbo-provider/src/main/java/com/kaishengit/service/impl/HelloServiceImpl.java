package com.kaishengit.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kaishengit.service.HelloService;

/**
 * @author liushuai
 */
@Service(timeout = 5000)
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {

        System.out.println("hello,world" + name);
    }
}
