package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.service.manage.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-07-10:38
 */
@CrossOrigin
@Controller
public class SpuController {
    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(@RequestParam("catalog3Id") String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        int i = spuService.saveSpuInfo(pmsProductInfo);
        if(i>=1){
            return "success";
        }else {
            return "false";
        }

    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(@RequestParam("spuId") String spuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrsList = spuService.spuSaleAttr(spuId);
        return pmsProductSaleAttrsList;
    }


    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(@RequestParam("spuId") String spuId) {
        List<PmsProductImage> pmsProductImageList = spuService.spuImageList(spuId);
        return pmsProductImageList;
    }



}
