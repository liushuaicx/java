package com.kaishengit.controller;

import com.kaishengit.entity.Article;
import com.kaishengit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id:\\d+}")
    @ResponseBody
    public Article findById(@PathVariable Integer id) {

        Article article = articleService.findById(id);
        System.out.println(article.getTitle());
        return article;
    }
    @GetMapping()
    public String findByPageNO(@RequestParam(defaultValue = "1",name = "p",required = false) Integer page, Model model) {

        List<Article> articleList = articleService.findByPageNO(page);

        model.addAttribute("articleList",articleList);
        return "book/articleList";
    }

}
