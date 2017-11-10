package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaType;

import java.util.List;
import java.util.Map;

public interface KaolaService {

    PageInfo<Kaola> findByPageNO(Integer pageNO);

    PageInfo<Kaola> findByPageNO(Integer pageNO, Map<String,Object> queryParam);

    Kaola finById(Integer id);

    void edit(Kaola kaola);

    List<KaolaType> findAllType();

    void delete(Integer id);

    void add(Kaola kaola);

    List<String> findAllPlace();

}
