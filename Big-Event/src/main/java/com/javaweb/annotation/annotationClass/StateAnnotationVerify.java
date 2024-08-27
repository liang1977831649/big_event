package com.javaweb.annotation.annotationClass;

import com.javaweb.annotation.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//解释ConstraintValidator继承了一个校验器，这个校验器有一个方法isValid，在这个方法里写入校验规则
//校验成功返回true，失败返回false
//传入了两个泛型，一个是支持的注解，另一个是注解修饰的类
public class StateAnnotationVerify implements ConstraintValidator<State,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null){
            return false;
        }
        if("已发布".equals(s)||"草稿".equals(s)){
            return true;
        }
        return false;
    }
}
