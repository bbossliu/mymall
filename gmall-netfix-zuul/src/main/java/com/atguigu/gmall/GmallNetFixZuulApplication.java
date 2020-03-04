package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author dalaoban
 * @create 2020-03-04-20:32
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDubbo
@EnableDiscoveryClient
public class GmallNetFixZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallNetFixZuulApplication.class,args);
    }
}
