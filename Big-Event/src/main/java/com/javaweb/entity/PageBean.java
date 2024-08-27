package com.javaweb.entity;

import lombok.Data;

import java.util.List;
@Data
public class PageBean<T> {
    private Integer pageNum;
    private List<T> items;
}
