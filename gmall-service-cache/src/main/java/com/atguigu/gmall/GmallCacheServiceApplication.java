package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author dalaoban
 * @create 2020-02-19-18:05
 */
@SpringBootApplication
@EnableDubbo
public class GmallCacheServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallCacheServiceApplication.class,args);
    }

}
