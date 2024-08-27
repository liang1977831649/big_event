package com.javaweb.annotation;

import com.javaweb.annotation.annotationClass.StateAnnotationVerify;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD})//修饰字段
@Retention(RetentionPolicy.RUNTIME)//运行时存在，刻印与反射生成class
@Documented
@Constraint(validatedBy = {StateAnnotationVerify.class})//接收具有校验功能的类的class
public @interface State {
    String message() default "{state的参数只能是’已发布‘或’草稿‘}";

    Class<?>[] groups() default {};//校验分组

    Class<? extends Payload>[] payload() default {};
}
