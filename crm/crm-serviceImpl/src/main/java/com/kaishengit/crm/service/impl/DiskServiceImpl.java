package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.example.DiskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.crm.files.FileStore;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author 刘帅
 */
@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Autowired
    @Qualifier("qiNiuFileStore")
    private FileStore fileStore;

    /**
     * 创建新文件夹
     * @param disk
     */
    @Override
    public void newDir(Disk disk) {

        disk.setUserId(disk.getUserId());
        disk.setUpdateTime(new Date());
        disk.setPid(disk.getPid());
        disk.setType(Disk.CREATE_DIR);
        diskMapper.insertSelective(disk);
    }

    /**
     * 根据pid查询显示列表
     * @param pid
     * @return
     */
    @Override
    public List<Disk> findAllDiskByPid(Integer pid) {

        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPidEqualTo(pid);
        return diskMapper.selectByExample(diskExample);

    }

    /**
     * 根据id查询Disk对象
     * @param id
     * @return
     */
    @Override
    public Disk findById(Integer id) {

        return diskMapper.selectByPrimaryKey(id);
    }

    /**
     * 文件上传
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @param fileSize    文件大小
     * @param userId      谁上传
     * @param pid         父文件夹的id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewFile(InputStream inputStream, String fileName,
                            long fileSize, Integer userId, Integer pid){

        String newFileName = null;
        try {
            newFileName = fileStore.saveFile(inputStream,fileName);
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


    }

    /**
     * 下载文件
     * @param id 文件id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InputStream downloadFile(Integer id) {

        Disk disk = diskMapper.selectByPrimaryKey(id);
        if (disk == null || disk.getType().equals(Disk.CREATE_DIR)) {
            throw new ServiceException("文件不存在或已被删除");
        }
        //下载数量加1
        disk.setDowloadCount(disk.getDowloadCount()+1);
        diskMapper.updateByPrimaryKey(disk);

        byte[] bytes = null;
        try {
            bytes = fileStore.getFile(disk.getSaveName());
        } catch (IOException e) {
            throw new RuntimeException("下载失败",e);
        }

        //byte转inputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        return inputStream;
    }
}
