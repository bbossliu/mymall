package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dalaoban
 * @create 2020-03-08-11:50
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableDubbo
public class GmallNItemWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallNItemWebApplication.class,args);
    }
}
