package com.atguigu.gmall.comtroller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dalaoban
 * @create 2020-03-01-21:56
 */
@RestController
@RequestMapping("/provider")

public class ProviderController {

    @GetMapping("/helloProvider")
    public String helloProvider(){
        return "你好,我是服务提供者";
    }
}
