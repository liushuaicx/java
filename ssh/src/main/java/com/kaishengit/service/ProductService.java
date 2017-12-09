package com.kaishengit.service;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.pojo.Product;
import com.kaishengit.util.Page;
import com.kaishengit.util.RequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘帅
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> findByPage() {
        return productDao.findByPage(0,100);
    }

    public void save(Product product) {

        productDao.save(product);
    }
    public void delete(Integer id) {
        productDao.deleteById(id);
    }

    public Product findById(Integer id) {
        return productDao.findById(id);
    }


    public Page<Product> findByQueryList(Integer pageNo, List<RequestQuery> requestQueryList) {

        return productDao.findByQueryList(pageNo,requestQueryList);
    }

    public void count() {
        System.out.println(productDao.count());
    }
}
