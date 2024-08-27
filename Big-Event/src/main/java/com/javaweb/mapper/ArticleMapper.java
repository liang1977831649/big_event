package com.javaweb.mapper;

import com.javaweb.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    void add(Article article);
    List<Article> list(Integer categoryId,String state,Integer userId);
    List<Article> listByCategoryId(Integer categoryId);
    Article detail(Integer id);
    void update(Article article);
    void delete(Integer id);
}
