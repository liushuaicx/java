package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.kaishengit.entity.Article;
import com.kaishengit.entity.ArticleExample;
import com.kaishengit.mapper.ArticleMapper;
import com.kaishengit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 刘帅
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Article findById(Integer id) {

        return articleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Article> findByPageNO(Integer pageNO) {
        PageHelper.startPage(pageNO,2);
        return articleMapper.selectByExample(new ArticleExample());
    }


}
