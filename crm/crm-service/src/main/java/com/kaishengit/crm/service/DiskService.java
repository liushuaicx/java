package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 刘帅
 */
public interface DiskService {


    /**
     * 创建新文件夹
     * @param disk
     */
    void newDir(Disk disk);

    /**
     * 根据pid查询显示列表
     * @param pid
     * @return
     */
    List<Disk> findAllDiskByPid(Integer pid);

    /**
     * 根据id查询Disk对象
     * @param pid
     * @return
     */
    Disk findById(Integer pid);

    /**
     * 文件上传
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param userId 谁上传
     * @param pid 父文件夹的id
     */
    void saveNewFile(InputStream inputStream, String fileName, long fileSize, Integer userId, Integer pid);

    /**
     * 下载文件
     * @param id
     * @return
     */
    InputStream downloadFile(Integer id) throws IOException;
}
