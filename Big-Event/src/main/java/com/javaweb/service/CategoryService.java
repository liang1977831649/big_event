package com.javaweb.service;

import com.javaweb.entity.Article;
import com.javaweb.entity.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);
    List<Category> list();
    Category detail(Integer id);
    Boolean del(Integer id);
    void update(Category category);
}
