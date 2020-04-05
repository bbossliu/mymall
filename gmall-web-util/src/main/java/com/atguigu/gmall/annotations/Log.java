package com.atguigu.gmall.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dalaoban
 * @create 2020-04-05-21:42
 */
@Target(ElementType.METHOD)/*定义注解的生效范围*/
@Retention(RetentionPolicy.RUNTIME)/*定义注解在虚拟机运行时也生效*/
public @interface Log {
}
