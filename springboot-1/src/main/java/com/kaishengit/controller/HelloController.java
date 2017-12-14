package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
public class HelloController {

    @GetMapping("/")
    public String Hello(Model model) {

        List<String> nameList = new ArrayList<>();
        nameList.add("tom");
        nameList.add("jack");
        model.addAttribute("message","hello");
        model.addAttribute("nameList",nameList);
        return "hello";
    }
}
