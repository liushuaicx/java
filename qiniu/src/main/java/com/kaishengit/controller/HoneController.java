package com.kaishengit.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 刘帅
 */
@Controller
public class HoneController {

    @GetMapping("/upload")
    public String upload(Model model,String fileName) {

        String accessKey = "RFM3L96nIlImv3o3Y7MQn3WGcP6zqDXmt1zpe029";
        String secretKey = "ChRp7R7c4NsL_zVcMyqRkZ8mAq89AfJxYgERoRnx";
        String bucket = "qiniuyun";
        String key = fileName;

        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody","{\"key\":\"${key}\"}");

        long expireSeconds = 3600;

        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(bucket,key,expireSeconds,putPolicy);
        model.addAttribute("upToken",upToken);
        return "home";
    }
}
