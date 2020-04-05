package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.mapper.PmsProductImageMapper;
import com.atguigu.gmall.mapper.PmsProductInfoMapper;
import com.atguigu.gmall.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.mapper.PmsProductSaleAttrValueMapper;
import com.atguigu.gmall.service.manage.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-01-16:48
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;

    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;

    @Autowired
    PmsProductImageMapper pmsProductImageMapper;

    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    List<PmsProductSaleAttrValue> pmsProductSaleAttrValues=null;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return pmsProductInfoMapper.select(pmsProductInfo);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttr(String productId) {
        //销售属性
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(productId);
        List<PmsProductSaleAttr> productSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);
        //销售属性值
        PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();

       if(!CollectionUtils.isEmpty(productSaleAttrs)){
           productSaleAttrs.stream()
                   .forEach((pmsProductSaleAttr1 )->{
                       pmsProductSaleAttrValue.setProductId(productId);
                       pmsProductSaleAttrValue.setSaleAttrId(pmsProductSaleAttr1.getSaleAttrId());
                       pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
                       if(!CollectionUtils.isEmpty(pmsProductSaleAttrValues)){
                           pmsProductSaleAttr1.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
                       }
                   });
       }

       return productSaleAttrs;
    }

    @Override
    public List<PmsProductImage> spuImageList(String productId) {
        PmsProductImage pmsProductImage = new PmsProductImage();
        pmsProductImage.setProductId(productId);
        return pmsProductImageMapper.select(pmsProductImage);
    }

    @Override
    public List<PmsProductSaleAttrValue> spuSaleAttrValue(PmsProductSaleAttr pmsProductSaleAttr) {
        return null;
    }

    //查询spu商品销售属性和销售属性值
    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String skuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.selectspuSaleAttrListCheckBySku(productId, skuId);
        return pmsProductSaleAttrList;
    }

    //保存spu商品信息
    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        int i = pmsProductInfoMapper.insertSelective(pmsProductInfo);
        if(i<1){
            return i;
        }else {
            String productInfoId = pmsProductInfo.getId();
            List<PmsProductImage> pmsProductImageList = pmsProductInfo.getSpuImageList();
            if(!CollectionUtils.isEmpty(pmsProductImageList)){
                pmsProductImageList.stream()
                        .forEach((pmsProductImage -> {
                            pmsProductImage.setProductId(productInfoId);
                            pmsProductImageMapper.insertSelective(pmsProductImage);
                        }));
            }
            List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
            if(!CollectionUtils.isEmpty(pmsProductSaleAttrList)){
                pmsProductSaleAttrList.stream()
                                        .forEach((pmsProductSaleAttr -> {
                                            pmsProductSaleAttr.setProductId(productInfoId);
                                            pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);
                                            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
                                            if(!CollectionUtils.isEmpty(spuSaleAttrValueList)){
                                                spuSaleAttrValueList.stream()
                                                                    .forEach((spuSaleAttrValue)->{
                                                                        spuSaleAttrValue.setProductId(productInfoId);
                                                                        pmsProductSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                                                                    });
                                            }
                                        }));
            }
        }
        return 1;
    }
}
