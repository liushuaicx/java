package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;

import java.util.List;

public interface KaolaService {

    PageInfo<Kaola> findList(Integer pageNO);
}
