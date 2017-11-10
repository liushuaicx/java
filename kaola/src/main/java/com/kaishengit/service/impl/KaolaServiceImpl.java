package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaExample;
import com.kaishengit.entity.KaolaType;
import com.kaishengit.entity.KaolaTypeExample;
import com.kaishengit.mapper.KaolaMapper;
import com.kaishengit.mapper.KaolaTypeMapper;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KaolaServiceImpl implements KaolaService {

    @Autowired
    private KaolaMapper kaolaMapper;
    @Autowired
    private KaolaTypeMapper kaolaTypeMapper;


    @Override
    public PageInfo<Kaola> findByPageNO(Integer pageNO) {
        PageHelper.startPage(pageNO, 15);
        KaolaExample kaolaExample = new KaolaExample();
        kaolaExample.setOrderByClause("id desc");
        List<Kaola> kaolaList = kaolaMapper.findWithTypeName();
        return new PageInfo<Kaola>(kaolaList);
    }

    @Override
    public PageInfo<Kaola> findByPageNO(Integer pageNO, Map<String, Object> queryParam) {
        PageHelper.startPage(pageNO, 15);
        List<Kaola> list = kaolaMapper.findByQueryParamWithType(queryParam);
        return new PageInfo<>(list);
    }

    @Override
    public Kaola finById(Integer id) {
        return kaolaMapper.selectByPrimaryKey(id);
    }

    @Override
    public void edit(Kaola kaola) {
        kaolaMapper.updateByPrimaryKeySelective(kaola);
    }

    @Override
    public List<KaolaType> findAllType() {
        return kaolaTypeMapper.selectByExample(new KaolaTypeExample());
    }

    @Override
    public void delete(Integer id) {
        kaolaMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void add(Kaola kaola) {

        kaola.setCommentNum(0);
        kaolaMapper.insert(kaola);
    }

    @Override
    public List<String> findAllPlace() {

        return kaolaMapper.findAllPlace();
    }
}
