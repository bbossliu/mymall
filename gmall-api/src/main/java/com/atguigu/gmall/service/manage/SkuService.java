package com.atguigu.gmall.service.manage;

import com.atguigu.gmall.bean.PmsSkuInfo;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-08-10:56
 */
public interface SkuService {

    List<PmsSkuInfo> skuList(String catalog3Id);

    void savePmsSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId , String ip);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    List<PmsSkuInfo> getAllSku(String catalog3Id);

    boolean checkPrice(String productSkuId, String productPrice);


}
