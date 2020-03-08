package com.atguigu.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dalaoban
 * @create 2020-02-12-10:23
 */
@SpringBootApplication
@MapperScan("com.atguigu.gmall.dao")
public class PassPortApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassPortApplication.class,args);
    }
}
