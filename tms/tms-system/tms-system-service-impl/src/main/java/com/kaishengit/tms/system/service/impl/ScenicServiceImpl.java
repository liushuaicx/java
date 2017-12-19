package com.kaishengit.tms.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.tms.entity.Scenic;
import com.kaishengit.tms.entity.ScenicAccount;
import com.kaishengit.tms.entity.ScenicExample;
import com.kaishengit.tms.mapper.ScenicAccountMapper;
import com.kaishengit.tms.mapper.ScenicMapper;
import com.kaishengit.tms.system.service.ScenicService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author liushuai
 */
@Service
public class ScenicServiceImpl implements ScenicService {

    private static Logger logger = LoggerFactory.getLogger(ScenicServiceImpl.class);
    @Autowired
    private ScenicMapper scenicMapper;

    private ScenicAccountMapper scenicAccountMapper;

    @Override
    public PageInfo<Scenic> findAllByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        ScenicExample scenicExample = new ScenicExample();
        List<Scenic> scenicList = scenicMapper.selectByExample(scenicExample);
        return new PageInfo<>(scenicList);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ScenicAccount newScenic(Scenic scenic) {
        //添加景区信息
        scenic.setCreateTime(new Date());
        scenicMapper.insert(scenic);
        //新增景区账号
        ScenicAccount scenicAccount = new ScenicAccount();
        scenicAccount.setCreateTime(new Date());
        scenicAccount.setScenicAccount(scenic.getScenicTel());
        scenicAccount.setScenicId(scenic.getId());
        String password = new Md5Hash("123").toString();
        scenicAccount.setScenicPassword(password);
        scenicAccount.setScenicState("正常营业");
        scenicAccountMapper.insert(scenicAccount);
        logger.info("添加新景区{},景区id为",scenic.getScenicName(),scenicAccount.getId());
        return scenicAccount;
    }

    @Override
    public Scenic findById(Integer scenicId) {

        Scenic scenic = scenicMapper.selectByPrimaryKey(scenicId);
        return scenic;
    }

    @Override
    public void edit(Scenic scenic) {

        scenicMapper.updateByPrimaryKey(scenic);
        logger.info("修改景区{}状态",scenic.getScenicName());
    }


}
