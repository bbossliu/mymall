package com.atguigu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

/**
 * @author dalaoban
 * @create 2020-03-01-21:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class GmallGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallGateWayApplication.class,args);
    }
}
