package com.javaweb.mapper;

import com.javaweb.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void add(Category category);
    List<Category> list(Integer id);
    Category detail(Integer id);
    void del(Integer id);
    void update(Category category);
}
