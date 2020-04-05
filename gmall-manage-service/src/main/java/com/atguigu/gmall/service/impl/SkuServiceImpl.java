package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.util.UuidUtils;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.mapper.*;
import com.atguigu.gmall.service.manage.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Reference
    ICacheService cacheService;

    public int a=10000;

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
    public PmsSkuInfo getSkuById(String skuId, String ip) throws InterruptedException {
        if(!StringUtils.isEmpty(skuId)){
            PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();

            //缓存Sku:id:操作
            String skuJson= String.valueOf(cacheService.getString("SKU:" + skuId + ":" + "INFO"));
            //设置缓存过期时间
            Integer expire=60*60*24;
            if(skuJson==null || skuJson.equals("null")){

                //生成uuid，作为redis分布式锁的value
                String token = UUID.randomUUID().toString();
                //分布式锁的key
                String key="SKU:"+skuId+":"+"LOCK";

                boolean nx = cacheService.set(key, token, "nx", "px", 500 * 1000);

                //加锁成功,访问数据库
                if(nx){
                    //二次确认redis中没有该sku
                    skuJson= String.valueOf(cacheService.getString("SKU:" + skuId + ":" + "INFO"));
                    if(skuJson==null || skuJson.equals("null")){
                            pmsSkuInfo.setId(skuId);
                            Example example = new Example(PmsSkuInfo.class);
                            example.createCriteria().andEqualTo("id",skuId);
                            pmsSkuInfo = pmsSkuInfoMapper.selectOneByExample(example);
                            if(Objects.isNull(pmsSkuInfo)){
                                expire=60*5;
                                skuJson=JSON.toJSONString("");//为空则设置缓存时间为5分钟，防止恶意对服务器进行攻击
                            }else {
                                //返回sku图片信息
                                PmsSkuImage pmsSkuImage = new PmsSkuImage();
                                pmsSkuImage.setSkuId(skuId);
                                List<PmsSkuImage> pmsSkuImageList = pmsSkuImageMapper.select(pmsSkuImage);
                                pmsSkuInfo.setSkuImageList(pmsSkuImageList);

                                //查询sku销售属性和sku销售属性值
                                PmsSkuSaleAttrValue pmsSkuSaleAttrValue = new PmsSkuSaleAttrValue();
                                pmsSkuSaleAttrValue.setSkuId(skuId);
                                List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValue);
                                pmsSkuInfo.setSkuSaleAttrValueList(pmsSkuSaleAttrValueList);
                                skuJson=JSON.toJSONString(pmsSkuInfo);
                            }
                            cacheService.setString("SKU:"+skuId+":"+"INFO",skuJson,expire);
                            //执行删除锁的操作
                            String newToken = cacheService.getString(key);
                            //判读自己锁和删除锁不是原子性操作，一样存在误删锁的问题
                            if(!StringUtils.isEmpty(newToken) && (newToken.equals(token))){
                                cacheService.delete(newToken);
                            }
                        }else {
                            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
                        }
                        return pmsSkuInfo;
                    }else {
                        //没有拿到分布式锁 ，自旋（该线程在睡眠5秒后重新尝试访问getSkuById方法）
                        TimeUnit.SECONDS.sleep(5);
                        //自旋继续申请锁
                        return getSkuById(skuId, ip);
                    }
                }else {
                pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
                return pmsSkuInfo;
            }
        }
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
        boolean b = false;
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(productSkuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        String price = skuInfo.getPrice();
        BigDecimal productPriceDecimal = new BigDecimal(productPrice);
        BigDecimal priceDecimal = new BigDecimal(price);

        if (!StringUtils.isEmpty(price) && productPriceDecimal.compareTo(priceDecimal)==0) {/*比较价格最好两个变量都是 BigDecimal 类型，准确，不易出错*/
            b = true;
        }
        return b;
    }

    @Override
    public List<PmsSkuInfo> skuInfoCheckBysaleAttrValueIds(String spuId) {
        List<PmsSkuInfo> pmsSkuInfoList=pmsSkuInfoMapper.skuInfoCheckBysaleAttrValueIds(spuId);
        return pmsSkuInfoList;
    }

    public static void main(String[] args) {
        String s = UuidUtils.generateUuid();
        System.out.println(s);
    }

}
