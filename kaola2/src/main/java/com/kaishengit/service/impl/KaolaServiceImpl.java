package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaExample;
import com.kaishengit.mapper.KaolaMapper;
import com.kaishengit.mapper.KaolaTypeMapper;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaolaServiceImpl implements KaolaService {

    @Autowired
    private KaolaMapper kaolaMapper;
    @Autowired
    private KaolaTypeMapper kaolaTypeMapper;

    @Override
    public PageInfo<Kaola> findList(Integer pageNO) {

        PageHelper.startPage(pageNO,10);
        KaolaExample kaolaExample = new KaolaExample();
        kaolaExample.setOrderByClause("id desc");
        List<Kaola> list = kaolaMapper.selectByExample(kaolaExample);
        return new PageInfo<>(list);
    }
}
