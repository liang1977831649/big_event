package com.javaweb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.javaweb.entity.Article;
import com.javaweb.entity.Category;
import com.javaweb.entity.PageBean;
import com.javaweb.mapper.ArticleMapper;
import com.javaweb.mapper.CategoryMapper;
import com.javaweb.service.ArticleService;
import com.javaweb.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Article article) {
        Map<String ,Object> map = ThreadLocalUtil.get();
        Integer createId =(Integer) map.get("id");
        article.setCreateUser(createId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> articlePageBean = new PageBean<>();

        //开启分页功能，会在查询这里加上limit语句
        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        //查询，返回集合
        List<Article> articles = articleMapper.list(categoryId, state,userId);
        //给文章分类名称赋值
        for (Article article : articles) {
            Category category = categoryMapper.detail(article.getCategoryId());
            article.setCategoryName(category.getCategoryName());
        }

        //强制类型转化,目的是为了算出记录数，可以转成这样的原因是Page实现了List接口，向下转型
        //封装了总记录数，当前页数，每页的大小等信息
        Page<Article> page=(Page<Article>) articles;

        //给articlePageBean赋值
        articlePageBean.setPageNum((int)page.getTotal());
        articlePageBean.setItems(page.getResult());
        return articlePageBean;
    }

    @Override
    public Article detail(Integer id) {
        Article article = articleMapper.detail(id);
        article.setCategoryName(categoryMapper.detail(article.getCategoryId()).getCategoryName());
        return article;
    }

    @Override
    public void update(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer  id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }

}
