package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dalaoban
 * @create 2020-02-20-17:40
 */
@SpringBootApplication
@EnableDubbo
public class GmallManageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallManageWebApplication.class,args);
    }
}
