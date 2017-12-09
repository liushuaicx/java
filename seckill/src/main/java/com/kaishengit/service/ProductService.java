package com.kaishengit.service;

import com.kaishengit.entity.Product;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * @author 刘帅
 */
public interface ProductService {


    /**
     * 保存商品
     * @param product
     * @param inputStream
     */
    void saveProduct(Product product, InputStream inputStream);

    /**
     * 查询商品列表
     * @return
     */
    List<Product> findAll();

    /**
     * 根据id查找商品
     * @param id
     * @return
     */
    Product findById(Integer id);

    /**
     * 秒杀
     * @param id
     */
    void secKill(Integer id);
}
