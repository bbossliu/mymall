package com.atguigu.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.service.manage.SkuService;
import com.atguigu.gmall.service.manage.SpuService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author dalaoban
 * @create 2020-03-08-14:00
 */
@Controller
@Api(value = "Sku商品详情",tags = {"商品详情"},basePath = "/item")
@RequestMapping("/item")
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    String k="";

    @GetMapping("getItem/{skuId}.html")
    public String getItem(@PathVariable("skuId") String skuId, ModelMap modelMap){
        try {
            PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId, "");

            if(!Objects.isNull(pmsSkuInfo)){
                modelMap.addAttribute("skuInfo",pmsSkuInfo);
                //根据spu和sku查询出所有的该spu下所有的销售属性和销售属性值
                List<PmsProductSaleAttr> pmsProductSaleAttrList = spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getSpuId(),skuId);
                modelMap.addAttribute("spuSaleAttrListCheckBySku",pmsProductSaleAttrList);
                List<PmsSkuInfo> pmsSkuInfoList=skuService.skuInfoCheckBysaleAttrValueIds(pmsSkuInfo.getSpuId());
                String skuInfosJsonMap="";
                if(!CollectionUtils.isEmpty(pmsSkuInfoList)){
                    Map<String, String> skuInfoMap = new HashMap<>(pmsSkuInfoList.size());
                    pmsSkuInfoList.stream()
                            .forEach((pmsSkuInfo1)->{
                                k="";
                                List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo1.getSkuSaleAttrValueList();
                                String v=pmsSkuInfo1.getId();
                                if(!CollectionUtils.isEmpty(skuSaleAttrValueList)){
                                    skuSaleAttrValueList.stream()
                                            .forEach((pmsSkuSaleAttrValue)->{
                                                String saleAttrValueId = pmsSkuSaleAttrValue.getSaleAttrValueId();
                                                k+=saleAttrValueId+"|";
                                            });
                                    skuInfoMap.put(k,v);
                                }

                            });
                    skuInfosJsonMap= JSON.toJSONString(skuInfoMap);
                }
                modelMap.addAttribute("skuInfosJsonMap",skuInfosJsonMap);
            }
            //根据销售属性值组合查询相应的sKu
            return "item";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "erro";
    }




}
