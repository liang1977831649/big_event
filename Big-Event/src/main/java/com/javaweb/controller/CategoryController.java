package com.javaweb.controller;

import com.javaweb.entity.Category;
import com.javaweb.entity.Result;
import com.javaweb.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody Category category){

        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> list = categoryService.list();
        return Result.success(list);
    }
    @GetMapping("/detail")
    public Result detail(@NotNull Integer id){
        Category detail = categoryService.detail(id);
        return Result.success(detail);
    }
    @DeleteMapping
    public Result delete(@NotNull Integer id){
        System.out.println("id="+id);
        Boolean del = categoryService.del(id);
        return del?Result.success():Result.error("该文章目录下有文章，不能删除");
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
}
