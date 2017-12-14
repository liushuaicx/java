package com.kaishengit.tms.system.api;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.Scenic;
import com.kaishengit.tms.system.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liushuai
 */
@Controller
@RequestMapping("/scenic")
public class ScenicController {

    @Autowired
    private ScenicService scenicService;

    @GetMapping("/")
    public String list(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo) {

        PageInfo<Scenic> scenicPageInfo = scenicService.findAllByPageNo(pageNo);

    }
}
