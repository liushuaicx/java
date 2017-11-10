package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kaola")
public class KaolaController {

    @Autowired
    private KaolaService kaolaService;

    @GetMapping()
    public String List(@RequestParam(defaultValue = "1",required = false,name = "p") Integer page,
                       Model model) {

        PageInfo<Kaola> pageInfo = kaolaService.findList(page);
        model.addAttribute("pageInfo",pageInfo);
        return "/product/list";
    }

}
