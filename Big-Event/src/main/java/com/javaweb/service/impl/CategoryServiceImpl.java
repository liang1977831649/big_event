package com.javaweb.service.impl;

import com.javaweb.entity.Article;
import com.javaweb.entity.Category;
import com.javaweb.mapper.ArticleMapper;
import com.javaweb.mapper.CategoryMapper;
import com.javaweb.service.ArticleService;
import com.javaweb.service.CategoryService;
import com.javaweb.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    protected ArticleMapper articleMapper;
    @Override
    public void add(@RequestBody Category category) {
        Map<String ,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        System.out.println("id="+id);
        category.setCreateUser(id);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String ,Object> map = ThreadLocalUtil.get();
        Integer id =(Integer) map.get("id");
        return categoryMapper.list(id);
    }

    @Override
    public Category detail(Integer id) {
        return categoryMapper.detail(id);
    }

    @Override
    public Boolean del(Integer id) {
        //先查找这个文章目录是否有对应的 文章，如果有，那就不能删除
        List<Article> articles = articleMapper.listByCategoryId(id);
        if(!articles.isEmpty()){
            return false;
        }
        categoryMapper.del(id);
        return true;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

}
