package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dalaoban
 * @create 2020-03-01-11:59
 */
@SpringBootApplication
@EnableDubbo
public class GmallFileUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallFileUploadApplication.class,args);
    }
}
