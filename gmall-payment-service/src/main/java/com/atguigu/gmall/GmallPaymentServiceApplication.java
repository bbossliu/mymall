package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author dalaoban
 * @create 2020-03-22-12:45
 */
@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.mapper")
public class GmallPaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentServiceApplication.class,args);
    }
}
