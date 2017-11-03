package com.kaishengit.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 刘帅
 */
@Controller
public class HelloController {

    //@RequestMapping(value = "/Hello",method = {RequestMethod.GET,RequestMethod.POST})
    @GetMapping("/Hello")
    public String Hello(Integer id, Model model) {

        System.out.println("id" + id);
        model.addAttribute("id",id);

        return "Hello";
    }

    @GetMapping("/save")
    public ModelAndView Hello(Integer id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Hello");
        modelAndView.addObject("id",id);

        return modelAndView;
    }
    @GetMapping("/find")
    public String Hello(@RequestParam(name = "class" ,required = false ,defaultValue = "2") Integer className, String name, Model model) {

        model.addAttribute("className",className);
        model.addAttribute("name",name);

        return "find";
    }

    /**
     * /class/22/name/jack
     * @param className
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/class/{className:\\d+}/name/{name}")
    public String find(@PathVariable Integer className, @PathVariable String name , Model model) {

        model.addAttribute("className",className);
        model.addAttribute("name",name);

        return "find";
    }
    @GetMapping("/login")
    public String login() {

        return "login";
    }
    @PostMapping("/login")
    public String login(String name, String password, MultipartFile photo, RedirectAttributes redirectAttributes) {

        System.out.println(name +"-->"+ password +"-->"+photo.getOriginalFilename());
        if (!photo.isEmpty()) {
            try {
                IOUtils.copy(photo.getInputStream(),new FileOutputStream("e:/upload/"+photo.getOriginalFilename()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("message","资料审核中");

        return "redirect:/login";
    }



}
