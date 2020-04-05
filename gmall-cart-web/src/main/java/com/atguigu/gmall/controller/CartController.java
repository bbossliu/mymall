package com.atguigu.gmall.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.annotations.LoginRequired;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.service.cart.CartService;
import com.atguigu.gmall.service.manage.SkuService;
import com.atguigu.gmall.util.CookieUtil;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-16-21:52
 */
@Controller
@Api(value = "购物车",tags = {"购物车"},basePath = "/cart")
@RequestMapping("cart")
public class CartController {

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;

    //购物车中商品总价格
    private static BigDecimal totalMount =null;

    @RequestMapping("cartList")/*购物车订单页面*/
    public String cartList(HttpServletRequest request , HttpServletResponse response , ModelMap modelMap){
        String memberId="12";
        List<OmsCartItem> cartInfo = new ArrayList<>();
        /*
        *判断有没有登录
        * 没有登录则直接从Cookie中查询
        * 有登录则判断缓存是否有该memberId的数据
        * 没有则查询数据库
        * 查询完成后将数据库中的数据放入缓存中
        * 返回查询结果cartList
        * */
        //memberId为空则直接从Cookie中查询
        if(StringUtils.isEmpty(memberId)){
            String cartList =CookieUtil.getCookieValue(request,"cartListCookie",true);
            cartInfo = JSON.parseArray(cartList, OmsCartItem.class);
        }else {
            cartInfo = cartService.cartList(memberId);
        }
        modelMap.addAttribute("cartList",cartInfo);
        /*调用算购物车总价的方法*/
        getTotalAmount(cartInfo);
        if (totalMount!=null){
            modelMap.addAttribute("totalMount",totalMount);
        }
        return "cartList";
    }

    private void getTotalAmount(List<OmsCartItem> omsCartItems) {

        if(!CollectionUtils.isEmpty(omsCartItems)){
            totalMount = new BigDecimal("0");
            omsCartItems.stream()
                    .filter(omsCartItem -> omsCartItem.getIsChecked().equals("1"))
                    .forEach((omsCartItem)->{
                        totalMount= totalMount.add(new BigDecimal(omsCartItem.getCartAllPrice()));
                    });
        }
    }

    @RequestMapping("addToCart")
    public String addToCart(String skuId , BigDecimal quantity , HttpServletRequest request , HttpServletResponse response,ModelMap modelMap){
        try {
            String memberId="12";
            List<OmsCartItem> omsCartItemList=new ArrayList<>();
            OmsCartItem omsCartItem = new OmsCartItem();
            /*调用商品服务查询商品信息*/
            PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId, "");

            modelMap.addAttribute("skuInfo",pmsSkuInfo);

            modelMap.addAttribute("skuNum",quantity);


            if(pmsSkuInfo!=null){
                /*将商品信息封装成购物车信息*/
                omsCartItem.setProductId(pmsSkuInfo.getSpuId());
                omsCartItem.setProductSkuId(skuId);
                omsCartItem.setQuantity(quantity);
                omsCartItem.setPrice(pmsSkuInfo.getPrice());
                /*缺少同步销售属性*/
                omsCartItem.setProductPic(pmsSkuInfo.getSkuDefaultImg());
                omsCartItem.setProductName(pmsSkuInfo.getSkuName());
                omsCartItem.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
                omsCartItem.setCreateDate(new Date());
                omsCartItem.setDeleteStatus(0);
                omsCartItem.setIsChecked("0");

                //判断是否登录
                if(StringUtils.isEmpty(memberId)){
                    //未登录，需要将新加入的商品信息加入到购物车中，就是cookie中
                    String cartList = CookieUtil.getCookieValue(request, "cartListCookie", true);
                    if(StringUtils.isEmpty(cartList)){
                        omsCartItemList.add(omsCartItem);
                    }else {
                        List<OmsCartItem> omsCartItemList1 = JSON.parseArray(cartList, OmsCartItem.class);
                        if(if_cart_exist(omsCartItemList1,omsCartItem)){
                            omsCartItemList1.stream()
                                    .forEach((omsCartItem1)->{
                                        /*存在相等的skuId，则增加数量并增加*/
                                        if(omsCartItem1.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                                            omsCartItem1.setQuantity(omsCartItem1.getQuantity().add(omsCartItem.getQuantity()));
                                            //计算总价格
                                            omsCartItem1.setCartAllPrice((omsCartItem1.getQuantity().multiply(new BigDecimal(omsCartItem1.getPrice()))).toPlainString());
                                        }
                                    });
                        }else {
                            omsCartItemList1.add(omsCartItem);
                        }
                    }
                    CookieUtil.setCookie(request,response,"cartListCookie", JSON.toJSONString(omsCartItemList),60*60*72,true);
                }else {
                    //在登录时，需要将购物车数据同步到数据库中
                   /* List<OmsCartItem> omsCartItemList1 = JSON.parseArray(cartList, OmsCartItem.class);
                    omsCartItemList1.stream()
                                    .forEach((omsCartItem1)->{
                                        cartService.addCart(omsCartItem1);
                                    });
                    CookieUtil.deleteCookie(request,response,"cartListCookie");*/
                    /*已经登录首先需要判断，该商品在购物车中是否存在*/
                    OmsCartItem omsCartItemFromDb = cartService.ifCartsExistByUser(memberId, skuId);
                    if(omsCartItemFromDb==null){
                        omsCartItem.setMemberId(memberId);
                        omsCartItem.setCartAllPrice(omsCartItem.getQuantity().multiply(new BigDecimal(omsCartItem.getPrice())).toString());
                        cartService.addCart(omsCartItem);
                    }else {
                        /*存在则更新购物车中该商品信息*/
                        omsCartItemFromDb.setQuantity(omsCartItem.getQuantity().add(omsCartItemFromDb.getQuantity()));
                        omsCartItemFromDb.setCartAllPrice(omsCartItemFromDb.getQuantity().multiply(new BigDecimal(omsCartItemFromDb.getPrice())).toString());
                        cartService.updataCart(omsCartItemFromDb);
                    }
                    /*更新缓存*/
                    cartService.flushCartCatch(memberId);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    private boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {

        boolean b = false;
        for (OmsCartItem cartItem : omsCartItems) {
            String productSkuId = cartItem.getProductSkuId();
            /*如果相等，说明即将添加到购物车的数据已经存在，不能覆盖，可以增加该商品的数量*/
            if (productSkuId.equals(omsCartItem.getProductSkuId())){
                b = true;
            }
        }
        return b;
    }

    @PostMapping("checkCart")
    public String checkCart(String skuId,String isChecked,ModelMap modelMap){
        OmsCartItem omsCartItem = cartService.ifCartsExistByUser("12", skuId);
        if(omsCartItem!=null){
            omsCartItem.setMemberId("12");
            omsCartItem.setIsChecked(isChecked);
            omsCartItem.setProductSkuId(skuId);
            cartService.updataCart(omsCartItem);
            cartService.flushCartCatch("12");
            List<OmsCartItem> omsCartItemList = cartService.cartList("12");
            modelMap.addAttribute("cartList",omsCartItemList);
            getTotalAmount(omsCartItemList);
            if (totalMount!=null){
                modelMap.addAttribute("totalMount",totalMount);
            }
        }
        return "cartInner";
    }
}
