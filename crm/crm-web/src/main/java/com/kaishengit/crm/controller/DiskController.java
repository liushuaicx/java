package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.files.QiNiuFileStore;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.web.result.AjaxResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/disk")
public class DiskController extends BaseController {

    @Autowired
    private DiskService diskService;
    @Autowired
    private DiskMapper diskMapper;

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

        System.out.println(disk.getName());

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

        String newFileName = null;
        try {
            QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
            newFileName = qiNiuFileStore.saveFile(inputStream,fileName);
        } catch (IOException e) {
            throw new  RuntimeException("文件上传异常",e);
        }

        Disk disk = new Disk();
        //字节转化为可阅读大小
        disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSize));
        disk.setType(Disk.CREATE_FILE);
        disk.setPid(pid);
        disk.setUserId(userId);
        disk.setUpdateTime(new Date());
        disk.setSaveName(newFileName);
        disk.setName(fileName);
        disk.setDowloadCount("0");
        //数据库保存文件
        diskMapper.insertSelective(disk);

        List<Disk> diskList = diskService.findAllDiskByPid(pid);
        return AjaxResult.successWithData(diskList);
    }

    @GetMapping("/download")
    public void download(@RequestParam(name = "_") Integer id ,
                         @RequestParam(required = false,defaultValue = "") String fileName,
                         HttpServletResponse response) {

        try {
            OutputStream outputStream = response.getOutputStream();

            Disk disk = diskMapper.selectByPrimaryKey(id);
            if (disk == null || disk.getType().equals(Disk.CREATE_DIR)) {
                throw new ServiceException("文件不存在或已被删除");
            }
            //下载数量加1
            disk.setDowloadCount(disk.getDowloadCount()+1);
            diskMapper.updateByPrimaryKey(disk);

            byte[] bytes = null;
            try {
                QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
                bytes = qiNiuFileStore.getFile(disk.getSaveName());
            } catch (IOException e) {
                throw new RuntimeException("下载失败",e);
            }

            //byte转inputStream
            InputStream inputStream = new ByteArrayInputStream(bytes);

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
