package com.javaweb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    //指定了Update，说明如果在某个请求方法参数里面加上@Validate(Category.Update.class)，就说明只在这个方法上起效果。
    @NotNull(groups = {Update.class})
    private Integer id;//主键ID
    @NotNull
    @Pattern(regexp = "\\S{2,32}")
    private String categoryName;//分类名称
    @NotNull
    @Pattern(regexp = "\\S{2,32}")
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    public interface Add{
        //这里说明：
        //1）在属性上面加上的校验注解括号里，添加group属性值，
        //2）如果在某个请求方法参数的@valide()括号里面加上:实体类名.接口名，就说明这种校验只对指定的方法有效
        //3）相当于是绑定请求方法和属性校验的作用
        //4）如果不写在检验注解上不写group属性的话，默认是全通用Default
        // public Result update(@RequestBody @Validated(Category.Update.class) Category category)
    }
    public interface Update{

    }
}
