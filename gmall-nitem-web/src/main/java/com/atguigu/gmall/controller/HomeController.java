package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.manage.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-08-12:06
 */
@Controller
public class HomeController {
//
//    @Reference
//    SkuService skuService;
//
//    @RequestMapping("{skuId}.html")
//    public String index(@PathVariable("skuId") String skuId,ModelMap modelMap) {
//        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId, "");
//        modelMap.addAttribute("skuInfo",pmsSkuInfo);
//        return "item";
//    }
//
//    @RequestMapping("item")
//    public String item(){
//       return "item";
//    }
//
//    @RequestMapping("indexxiner")
//    public String indexxiner(){
//       return "indexxiner";
//    }
}
