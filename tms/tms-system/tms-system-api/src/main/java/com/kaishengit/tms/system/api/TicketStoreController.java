package com.kaishengit.tms.system.api;

import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.StoreAccount;
import com.kaishengit.tms.entity.TicketStore;
import com.kaishengit.tms.system.files.QiNiuFileStore;
import com.kaishengit.tms.system.service.TicketStoreService;
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
@RequestMapping("/ticket")
public class TicketStoreController {

    @Autowired
    private TicketStoreService ticketStoreService;

    @GetMapping("/")
    public String list(@RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNo,
                       Model model) {

        PageInfo<TicketStore> ticketStorePageInfo = ticketStoreService.findAllByPageNo(pageNo);
        model.addAttribute("ticketStorePageInfo",ticketStorePageInfo);
        return "ticket/list";
    }

    @GetMapping("/newTicketStore")
    public String newTicketStore() {
        return "ticket/new";
    }

    /**
     *添加售票点
     * @param ticketStore
     * @param file 营业执照
     * @param file2 身份证正面
     * @param file3 身份证反面
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PostMapping("/newTicketStore")
    public String newTicketStore(TicketStore ticketStore, MultipartFile file,
                                 MultipartFile file2,
                                 MultipartFile file3,
                                 RedirectAttributes redirectAttributes) throws IOException {

        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        InputStream inputStream2 = file2.getInputStream();
        InputStream inputStream3 = file3.getInputStream();
        //获得文件原名
        String fileName = file.getOriginalFilename();
        String fileName2 = file2.getOriginalFilename();
        String fileName3 = file3.getOriginalFilename();

        String newFileName = null;
        String newFileName2 = null;
        String newFileName3 = null;
        try {
            QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
            newFileName = qiNiuFileStore.saveFile(inputStream,fileName);
            newFileName2 = qiNiuFileStore.saveFile(inputStream2,fileName2);
            newFileName3 = qiNiuFileStore.saveFile(inputStream3,fileName3);
            //保存ticketStore
            ticketStore.setStoreAttachment(newFileName);
            String newFileName4 = newFileName2 +","+newFileName3;
            ticketStore.setStoreManageAttachment(newFileName4);
            StoreAccount storeAccount = ticketStoreService.newTicketStore(ticketStore);
            redirectAttributes.addFlashAttribute("message","售票点保存成功 , 账号为<"+storeAccount.getStoreAccount()+"> , 默认密码为 <123>");
        }catch (IOException ex) {
            redirectAttributes.addFlashAttribute("message","文件上传失败");
            return "redirect:/newTicketStore";
        }
        return "redirect:/ticket/";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editTicketStore(@PathVariable(name = "id",required = false) Integer ticketStoreId,Model model) {

        model.addAttribute("ticketStore",ticketStoreService.findByTicketStoreId(ticketStoreId));
        return "ticket/edit";
    }

    @PostMapping("/edit/{id:\\d+}")
    public String editTicketStore(TicketStore ticketStore, MultipartFile file,
                                  MultipartFile file2,
                                  MultipartFile file3,RedirectAttributes redirectAttributes) throws IOException {

        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        InputStream inputStream2 = file2.getInputStream();
        InputStream inputStream3 = file3.getInputStream();
        //获得文件原名
        String fileName = file.getOriginalFilename();
        String fileName2 = file2.getOriginalFilename();
        String fileName3 = file3.getOriginalFilename();

        String newFileName = null;
        String newFileName2 = null;
        String newFileName3 = null;
        try {
            QiNiuFileStore qiNiuFileStore = new QiNiuFileStore();
            newFileName = qiNiuFileStore.saveFile(inputStream,fileName);
            newFileName2 = qiNiuFileStore.saveFile(inputStream2,fileName2);
            newFileName3 = qiNiuFileStore.saveFile(inputStream3,fileName3);
            //保存ticketStore
            ticketStore.setStoreAttachment(newFileName);
            String newFileName4 = newFileName2 +","+newFileName3;
            ticketStore.setStoreManageAttachment(newFileName4);
            ticketStoreService.editTicketStore(ticketStore);
            redirectAttributes.addFlashAttribute("message","修改成功");

        }catch (IOException ex) {
            redirectAttributes.addFlashAttribute("message", "文件上传失败");
            return "redirect:/newTicketStore";
        }
        return "redirect:/ticket/";
    }

}
