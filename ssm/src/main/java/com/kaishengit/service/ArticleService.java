package com.kaishengit.service;

import com.kaishengit.entity.Article;

import java.util.List;

/**
 * @author 刘帅
 */
public interface ArticleService {

    Article findById(Integer id);
    List<Article> findByPageNO(Integer page);
}
