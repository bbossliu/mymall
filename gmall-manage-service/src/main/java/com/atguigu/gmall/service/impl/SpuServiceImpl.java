package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.service.manage.SpuService;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-01-16:48
 */
@Service
public class SpuServiceImpl implements SpuService {
    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        return null;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttr(String productId) {
        return null;
    }

    @Override
    public List<PmsProductSaleAttrValue> spuSaleAttrValue(PmsProductSaleAttr pmsProductSaleAttr) {
        return null;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
        return null;
    }
}
