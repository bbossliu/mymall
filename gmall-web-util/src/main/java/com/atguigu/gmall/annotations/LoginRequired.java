package com.atguigu.gmall.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dalaoban
 * @create 2020-02-19-15:39
 */
@Target(ElementType.METHOD) //定义注解生效范围时方法
@Retention(RetentionPolicy.RUNTIME)//定义注解在虚拟机运行时也生效
public @interface LoginRequired {

    boolean loginSuccess() default true;
}
