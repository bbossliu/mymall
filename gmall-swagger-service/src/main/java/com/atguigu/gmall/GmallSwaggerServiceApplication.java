package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dalaoban
 * @create 2020-02-28-23:19
 */
@SpringBootApplication
@EnableDubbo
@EnableSwagger2
public class GmallSwaggerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallSwaggerServiceApplication.class,args);
    }
}
