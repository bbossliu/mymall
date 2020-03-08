package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.mapper.*;
import com.atguigu.gmall.service.manage.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-08-10:56
 */
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Override
    public List<PmsSkuInfo> skuList(String catalog3Id) {
        return null;
    }

    @Override
    public void savePmsSkuInfo(PmsSkuInfo pmsSkuInfo) {
        int insert = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuInfoId = pmsSkuInfo.getId();  //主键返回策略

        //平台属性值
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        if(!CollectionUtils.isEmpty(skuAttrValueList)){
            skuAttrValueList.stream()
                            .forEach((pmsSkuAttrValue)->{
                                pmsSkuAttrValue.setSkuId(skuInfoId);
                                pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
                            });
        }

        //销售属性值
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        if(!CollectionUtils.isEmpty(skuSaleAttrValueList)){
            skuSaleAttrValueList.stream()
                                .forEach((pmsSkuSaleAttrValue)->{
                                    pmsSkuSaleAttrValue.setSkuId(skuInfoId);
                                    pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
                                });
        }
        //sku图片信息

        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        if(!CollectionUtils.isEmpty(skuImageList)){
            skuImageList.stream()
                        .forEach((pmsSkuImage)->{
                            pmsSkuImage.setSkuId(skuInfoId);
                            pmsSkuImageMapper.insertSelective(pmsSkuImage);
                        });
        }

    }

    @Override
    public PmsSkuInfo getSkuById(String skuId, String ip) {
        return null;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        return null;
    }

    @Override
    public List<PmsSkuInfo> getAllSku(String catalog3Id) {
        return null;
    }

    @Override
    public boolean checkPrice(String productSkuId, String productPrice) {
        return false;
    }
}
