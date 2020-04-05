package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author dalaoban
 * @create 2020-03-21-20:13
 */
@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.mapper")
public class GmallOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallOrderServiceApplication.class,args);
    }
}
