package com.atguigu.gmall.controller;

import com.atguigu.gmall.annotations.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dalaoban
 * @create 2020-02-20-17:49
 */
@Controller
public class SearchController {

    @RequestMapping("/main")
    @LoginRequired
    public String mian(){
        return "main";
    }
}
