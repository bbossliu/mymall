package com.atguigu.gmall.mapper;

import com.atguigu.gmall.bean.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-07-22:15
 */
public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {

    List<PmsSkuInfo>  selectSkuInfoAndSkuImgBySkuId(PmsSkuInfo pmsSkuInfo);

    List<PmsSkuInfo> skuInfoCheckBysaleAttrValueIds(@Param("spuId") String spuId);
}
