package com.kaishengit.service.impl;

import com.kaishengit.service.ProductService;

import java.util.Arrays;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    public List<String> findAllProductName() {

        return Arrays.asList("手机","电脑","冰箱");
    }
}
