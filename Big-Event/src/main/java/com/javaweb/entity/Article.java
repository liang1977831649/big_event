package com.javaweb.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.javaweb.annotation.State;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Article {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotNull
    @Pattern(regexp = "\\S{3,10}")
    private String title;//文章标题
    @NotNull
    private String content;//文章内容
    @NotNull
    private String coverImg;//封面图像
    @State
    private String state;//发布状态 已发布|草稿
    @NotNull
    private Integer categoryId;//文章分类id
    private String categoryName;

    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    public interface Add{

    }
    public interface Update{

    }
}
