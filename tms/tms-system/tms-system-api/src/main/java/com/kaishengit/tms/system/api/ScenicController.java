package com.kaishengit.tms.system.api;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.Scenic;
import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.exception.ServiceException;
import com.kaishengit.tms.system.files.QiNiuFileStore;
import com.kaishengit.tms.system.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liushuai
 */
@Controller
@RequestMapping("/scenic")
public class ScenicController {

    @Autowired
    private ScenicService scenicService;

    @GetMapping("/")
    public String list(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                       Model model) {

        PageInfo<Scenic> scenicPageInfo = scenicService.findAllByPageNo(pageNo);
        model.addAttribute("scenicPageInfo",scenicPageInfo);
        return "scenic/list";
    }
    @GetMapping("/new")
    public String newScenic() {
        return "scenic/new";
    }
    @PostMapping("/new")
    public String newScenic(Scenic scenic, MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        //获取文件大小
        long fileSize = file.getSize();
        //获取文件原名
        String fileName = file.getOriginalFilename();
        String newFileName = null;

        try {
            //上传到七牛云
            QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
            newFileName = qiNiuFileStore.saveFile(inputStream,fileName);

            scenic.setScenicAttachment(newFileName);
            ScenicAccount scenicAccount = scenicService.newScenic(scenic);
            redirectAttributes.addFlashAttribute("message","新增成功账号为<"+scenicAccount.getScenicAccount()+">,默认密码为<123>");
        }catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }catch (IOException ex) {
            throw new RuntimeException("文件上传异常",ex);
        }
        return "redirect:/scenic/";
    }
    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") Integer scenicId,Model model) {
        model.addAttribute("scenic",scenicService.findById(scenicId));
        return "/scenic/edit";
    }

    @PostMapping("/edit")
    public String edit(Scenic scenic, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            scenicService.edit(scenic);
        }else {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件大小
            long fileSize = file.getSize();
            //获取文件原名
            String fileName = file.getOriginalFilename();
            String newFileName = null;
            //上传到七牛云
            QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
            newFileName = qiNiuFileStore.saveFile(inputStream,fileName);

            scenic.setScenicAttachment(newFileName);
            scenicService.edit(scenic);
        }

        return "redirect:/scenic/";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") Integer scenicId,Model model) {
        model.addAttribute("scenic",scenicService.findById(scenicId));
        return "scenic/detail";
    }

}
