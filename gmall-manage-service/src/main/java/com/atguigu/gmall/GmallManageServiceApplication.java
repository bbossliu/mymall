package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author dalaoban
 * @create 2020-02-27-22:53
 */
@MapperScan(basePackages = "com.atguigu.gmall.mapper")
@SpringBootApplication
@EnableDubbo
public class GmallManageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallManageServiceApplication.class,args);
    }
}
