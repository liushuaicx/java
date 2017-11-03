package com.kaishengit.service.impl;

import com.kaishengit.entity.Article;
import com.kaishengit.mapper.ArticleMapper;
import com.kaishengit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article findById(int id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }
}
