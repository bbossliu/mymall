package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dalaoban
 * @create 2020-02-18-18:10
 */
@SpringBootApplication
@EnableSwagger2
@EnableDubbo
@EnableDiscoveryClient
public class GmallPassPortApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallPassPortApplication.class,args);
    }
}
