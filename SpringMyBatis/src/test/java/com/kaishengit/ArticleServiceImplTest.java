package com.kaishengit;

import com.kaishengit.entity.Article;
import com.kaishengit.service.impl.ArticleServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleServiceImplTest extends BaseTest {

    @Autowired
    private ArticleServiceImpl articleService;

    @Test
    public void findById() {
        Article article = articleService.findById(33);
        System.out.println(article);
    }
}
