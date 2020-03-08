package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dalaoban
 * @create 2020-03-01-21:47
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class GmallTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallTestApplication.class,args);
    }
}
