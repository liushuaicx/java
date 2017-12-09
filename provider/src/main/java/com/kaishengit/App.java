package com.kaishengit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-provider.xml");
        context.start();
        System.in.read();
    }
}
