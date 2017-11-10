package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.kaishengit.entity.Kaola;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/product")
public class KaolaController {

    @Autowired
    private KaolaService kaolaService;

    @GetMapping
    public String list(@RequestParam(required = false, defaultValue = "1") Integer p,
                       @RequestParam(required = false,defaultValue = "") String productName,
                       @RequestParam(required = false,defaultValue = "") String place,
                       @RequestParam(required = false,defaultValue = "") Integer typeId,
                       Model model) {

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("productName",productName);
        queryParam.put("place",place);
        queryParam.put("typeId",typeId);
        PageInfo<Kaola> PageInfo = kaolaService.findByPageNO(p,queryParam);

        model.addAttribute("typeList",kaolaService.findAllType());
        model.addAttribute("place",kaolaService.findAllPlace());
        model.addAttribute("pageInfo", PageInfo);
        return "/product/list";
    }

    @GetMapping("/{id:\\d+}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("kaola", kaolaService.finById(id));
        return "/product/show";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("kaola", kaolaService.finById(id));
        model.addAttribute("kaolaType", kaolaService.findAllType());
        return "/product/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String edit(Kaola kaola, RedirectAttributes redirectAttributes) {
        kaolaService.edit(kaola);
        redirectAttributes.addFlashAttribute("message", "修改成功");
        return "redirect:/product/" + kaola.getId();
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        kaolaService.delete(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/product";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("kaolaType", kaolaService.findAllType());

        return "/product/add";
    }

    @PostMapping("/add")
    public String add(Kaola kaola,RedirectAttributes redirectAttributes) {

        kaolaService.add(kaola);
        redirectAttributes.addFlashAttribute("message","保存成功");
        return "redirect:/product";
    }


}
