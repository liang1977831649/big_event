package com.javaweb.controller;

import com.javaweb.entity.Article;
import com.javaweb.entity.PageBean;
import com.javaweb.entity.Result;
import com.javaweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,Integer pageSize,
                                          @RequestParam(required = false)  Integer categoryId,
                                          @RequestParam(required = false)  String state){
        PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }
    @GetMapping("/detail")
    public Result detail( Integer id){
        Article detail = articleService.detail(id);
        return Result.success(detail);
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        articleService.update(article);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return  Result.success();
    }
}