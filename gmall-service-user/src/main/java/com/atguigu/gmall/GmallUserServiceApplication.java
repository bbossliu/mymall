package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dalaoban
 * @create 2020-02-19-13:08
 */
@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages ={"com.atguigu.gmall.user.mapper"})
public class GmallUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallUserServiceApplication.class,args);
    }

}
