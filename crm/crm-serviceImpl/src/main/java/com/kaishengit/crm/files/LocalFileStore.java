package com.kaishengit.crm.files;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

/**
 * 本地磁盘上传下载
 * @author 刘帅
 */
@Component
public class LocalFileStore implements FileStore {

    @Value("${upload.path}")
    private String filePath;

    /**
     * 保存文件
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @return 文件存放路径
     * @throws IOException
     */
    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException {

        //重命名
        String newFileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        //本地磁盘
        FileOutputStream outputStream = new FileOutputStream(new File(filePath,newFileName));

        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return newFileName;
    }

    /**
     * 下载文件
     * @param fileName 文件名或者文件路径
     * @return 文件的字节数组
     * @throws IOException
     */
    @Override
    public byte[] getFile(String fileName) throws IOException {

        FileInputStream inputStream = new FileInputStream(new File(filePath,fileName));
        return IOUtils.toByteArray(inputStream);
    }
}
