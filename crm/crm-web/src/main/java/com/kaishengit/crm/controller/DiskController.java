package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.web.result.AjaxResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/disk")
public class DiskController extends BaseController {

    @Autowired
    private DiskService diskService;

    @GetMapping()
    public String disk(Model model,@RequestParam(required = false,defaultValue = "0",name = "_") Integer pid) {

        List<Disk> diskList = diskService.findAllDiskByPid(pid);
        if (pid != 0) {
            Disk disk = diskService.findById(pid);
            model.addAttribute("disk",disk);
        }

        model.addAttribute("diskList",diskList);
        return "disk";
    }

    @PostMapping("/new")
    @ResponseBody
    public AjaxResult newDir(Disk disk) {

        diskService.newDir(disk);
        List<Disk> diskList = diskService.findAllDiskByPid(disk.getPid());

        return AjaxResult.successWithData(diskList);
    }

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(Integer pid, Integer userId, MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return AjaxResult.error("文件不可缺少");
        }
        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        //获取文件大小
        long fileSize = file.getSize();
        //获取文件真正的 名称
        String fileName = file.getOriginalFilename();

        diskService.saveNewFile(inputStream,fileName,fileSize,userId,pid);

        List<Disk> diskList = diskService.findAllDiskByPid(pid);
        return AjaxResult.successWithData(diskList);
    }

    @GetMapping("/download")
    public void download(@RequestParam(name = "_") Integer id ,
                         @RequestParam(required = false,defaultValue = "") String fileName,
                         HttpServletResponse response) {

        try {
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = diskService.downloadFile(id);

            //判断是下载还是预览
            if (StringUtils.isNotEmpty(fileName)) {

                //设置mime类型'
                response.setContentType("application/octet-stream");
                fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
                //设置响应头
                response.setHeader("Content-Disposition","attachment; filename= \""+fileName+"\"");
            }
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new NotFoundException();
        }


    }



}
