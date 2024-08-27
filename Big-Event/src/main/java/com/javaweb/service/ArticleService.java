package com.javaweb.service;

import com.javaweb.entity.Article;
import com.javaweb.entity.Category;
import com.javaweb.entity.PageBean;

public interface ArticleService {
    void add(Article article);
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    Article detail(Integer id);
    void update(Article article);
    void delete(Integer id);
}
