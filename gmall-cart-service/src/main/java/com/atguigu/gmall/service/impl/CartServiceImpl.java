package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.mapper.OmsCartItemMapper;
import com.atguigu.gmall.service.cart.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dalaoban
 * @create 2020-03-16-23:10
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    OmsCartItemMapper omsCartItemMapper;

    @Reference
    ICacheService cacheService;

    /*
    * 更具人员ID判断购物车中是否存在该商品信息
    * */
    @Override
    public OmsCartItem ifCartsExistByUser(String memberId, String skuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        OmsCartItem omsCartItem1 = omsCartItemMapper.selectOne(omsCartItem);
        return omsCartItem1;
    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        if (StringUtils.isNotBlank(omsCartItem.getMemberId())){
            omsCartItemMapper.insert(omsCartItem);
        }
    }

    //购物车中存在该加入的商品则调用更新
    @Override
    public void updataCart(OmsCartItem omsCartItemFromDb) {
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo("id",omsCartItemFromDb.getId());
        omsCartItemMapper.updateByExampleSelective(omsCartItemFromDb,example);
    }

    //更细缓存
    @Override
    public void flushCartCatch(String memberId) {
        Map<String,String> map=new HashMap<>();
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo("memberId",memberId);
        List<OmsCartItem> omsCartItemList = omsCartItemMapper.selectByExample(example);
        omsCartItemList.stream()
                        .forEach((omsCartItem)->{
                            map.put(omsCartItem.getProductSkuId(), JSON.toJSONString(omsCartItem));
                        });
        /*结构
             K                V
        * memberId  map<skuId , cartItem->JSON>
        * V 用Hashmap<>集合表示,方便修改，更新，查询数据
        * */
        cacheService.hmset("USER:"+memberId+":CART",map);
    }

    /*
    * 查询购物车列表
    * */
    @Override
    public List<OmsCartItem> cartList(String memberId) {
        List<String> cartJsonStr = cacheService.hval("USER:" + memberId + ":CART");
        List<OmsCartItem> omsCartItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartJsonStr)){
            cartJsonStr.stream()
                    .forEach((str)->{
                        omsCartItemList.add(JSON.parseObject(str,OmsCartItem.class));
                    });
        }
        return omsCartItemList;
    }

    /*
    * 根据商品Id删除购物车商品信息
    * */
    @Override
    public void deletByOrderItemProductSkuId(String productSkuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setProductSkuId(productSkuId);
        omsCartItemMapper.delete(omsCartItem);
    }

    /*
    * 根据会员Id删除购物车信息
    * */
    @Override
    public void delCartByMemberid(String memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItemMapper.delete(omsCartItem);
        cacheService.delete("USER:" + memberId + ":CART");
    }
}
