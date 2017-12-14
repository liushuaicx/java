package com.kaishengit.tms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-context.xml");

        context.start();
        logger.info("tms-system-provider 启动成功");
        System.in.read();
    }
}
