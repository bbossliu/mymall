package com.atguigu.gmall.mapper;

import com.atguigu.gmall.bean.PmsProductSaleAttr;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-07-10:55
 */
public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr> {

   List<PmsProductSaleAttr> selectspuSaleAttrListCheckBySku(@Param("productId") String productId, @Param("skuId") String skuId);
}
