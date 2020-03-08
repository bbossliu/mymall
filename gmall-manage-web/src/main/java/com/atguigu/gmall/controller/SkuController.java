package com.atguigu.gmall.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.manage.SkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-08-10:14
 */
@CrossOrigin
@Controller
public class SkuController {

    @Reference
    SkuService skuService;

    @RequestMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        String skuDefaultImg = pmsSkuInfo.getSkuDefaultImg();
        if(StringUtils.isEmpty(skuDefaultImg)){
            List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
            if(!CollectionUtils.isEmpty(skuImageList)){
                pmsSkuInfo.setSkuDefaultImg(skuImageList.get(0).getImgUrl());
            }
        }
        skuService.savePmsSkuInfo(pmsSkuInfo);
        return "success";
    }
}
