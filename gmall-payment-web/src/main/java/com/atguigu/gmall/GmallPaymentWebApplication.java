package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dalaoban
 * @create 2020-03-22-12:47
 */
@SpringBootApplication
@EnableDubbo
@EnableSwagger2
@EnableDiscoveryClient
public class GmallPaymentWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentWebApplication.class,args);
    }
}
