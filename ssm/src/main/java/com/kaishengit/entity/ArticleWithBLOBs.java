package com.kaishengit.entity;

public class ArticleWithBLOBs extends Article {
    private String content;

    private String simplecontent;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSimplecontent() {
        return simplecontent;
    }

    public void setSimplecontent(String simplecontent) {
        this.simplecontent = simplecontent;
    }
}