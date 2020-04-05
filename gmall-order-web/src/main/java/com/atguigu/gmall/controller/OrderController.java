package com.atguigu.gmall.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.bean.OmsOrder;
import com.atguigu.gmall.bean.OmsOrderItem;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.service.cart.CartService;
import com.atguigu.gmall.service.manage.SkuService;
import com.atguigu.gmall.service.order.OrderService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dalaoban
 * @create 2020-03-21-20:27
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Reference
    CartService cartService;

    @Reference
    ICacheService cacheService;

    @Reference
    OrderService orderService;

    @Reference
    SkuService skuService;
    @RequestMapping("toTrade")
    public String toTrade(ModelMap modelMap){
        /*所有勾选商品的总价格*/
        BigDecimal totalAmount=new BigDecimal("0");
        String memberId="12";
        modelMap.put("memberId", memberId);
        modelMap.put("nickname", "大老板");

        /*
        * 1、首先需要判断是否登录
        * 2、没有登录提示登录
        * 3、已经登录则根据MemberId查询购物车已勾选的商品信息
        * 4、获取购物车中的商品信息，和收货地址并且默认选中标识的收货得治
        * */
        if(!StringUtils.isEmpty(memberId)){
            List<OmsCartItem> omsCartItemList = cartService.cartList(memberId);
            List<OmsOrderItem> omsOrderItems = new ArrayList<>();
            OmsOrderItem omsOrderItem = new OmsOrderItem();
            for(OmsCartItem omsCartItem:omsCartItemList){
                omsOrderItem.setProductPrice(omsCartItem.getPrice());
                omsOrderItem.setOrderSn(omsCartItem.getProductSn());
                omsOrderItem.setProductQuantity(omsCartItem.getQuantity().toString());
                omsOrderItem.setProductName(omsCartItem.getProductName());
                omsOrderItems.add(omsOrderItem);
                /*计算总价格*/
                String isChecked = omsCartItem.getIsChecked();
                if(!StringUtils.isEmpty(isChecked) && isChecked.equals("1")){
                    totalAmount=totalAmount.add(new BigDecimal(omsCartItem.getCartAllPrice()));
                }
            }
            modelMap.addAttribute("totalAmount",totalAmount);
            modelMap.addAttribute("orderDetailList",omsOrderItems);
            /*需要生成唯一的交易码*/
            String tradeCode= UUID.randomUUID().toString();
            cacheService.setex("USER:"+memberId+":TRADE",60*15,tradeCode);
            modelMap.addAttribute("tradeCode",tradeCode);
        }
        return "trade";
    }

    @PostMapping("submitOrder")
    public ModelAndView submitOrder(String tradeCode,BigDecimal totalAmount){
       /*
       * 1、根据tradeCode判断是否为第一次提交的订单
       * 2、生成相应的订单，并将购物车中的数据同步到订单详情表
       * 3、校验价格，sku商品的价格
       * 4、校验库存，远程调用库存系统
       * 5、删除购物车中相应的数据
       * 6、删除缓存的购物车数据
       * 7、带着totalAmount和outTradeNumber重定向到支付页面（外部订单号，用来和其他系统进行交互，比如连接Alipay（支付宝）时使用）
       * */
        String memberId="12";
        String strCode = orderService.checkTradeCode(memberId, tradeCode);
        /*根据tradeCode判断是否为第一次提交的订单*/
        if(!StringUtils.isEmpty(strCode)&& strCode.equals("OK")){
            /*生成相应的订单，并将购物车中的数据同步到订单详情表*/
            OmsOrderItem omsOrderItem = new OmsOrderItem();
            OmsOrder omsOrder = new OmsOrder();
            omsOrder.setAutoConfigDay("7");/*7天后自动收货*/
            omsOrder.setCreateTime(new Date());
            omsOrder.setMemberId(memberId);
            omsOrder.setMemberUsername("大老板");
            omsOrder.setNote("能快点儿送达吗？我急用！！！");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");
            /*将毫秒时间戳拼接到外部订单号*/ /*将时间字符串拼接到外部订单号*/
            final String  outTradeNumber = "gmall"+ System.currentTimeMillis()+ simpleDateFormat.format(new Date()); ;
            omsOrder.setOrderSn(outTradeNumber);
            omsOrder.setPayAmount(totalAmount.toString());
            omsOrder.setOrderType("1");
         /*   UmsMemberReceiveAddress umsMemberReceiveAddress = userService.getReceiveAddressByReceiveAddressId(receiveAddressId);
            omsOrder.setReceiverCity(umsMemberReceiveAddress.getCity());
            omsOrder.setReceiverDetailAddress(umsMemberReceiveAddress.getDetailAddress());
            omsOrder.setReceiverName(umsMemberReceiveAddress.getName());
            omsOrder.setReceiverPhone(umsMemberReceiveAddress.getPhoneNumber());
            omsOrder.setReceiverPostCode(umsMemberReceiveAddress.getPostCode());
            omsOrder.setReceiverProvince(umsMemberReceiveAddress.getProvince());
            omsOrder.setReceiverRegion(umsMemberReceiveAddress.getRegion());*/
            /*当前日期加一天 ，一天后配送*/
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            Date time = calendar.getTime();
            omsOrder.setReceiveTime(time);
            omsOrder.setSourceType("0");/*0:PC 1:APP*/
            omsOrder.setStatus("0");
            omsOrder.setTotalAmount(totalAmount.toString());
            List<OmsOrderItem> omsOrderItemList = new ArrayList<>();
            List<String> strJsonCart = cacheService.hval("USER:" + memberId + ":CART");
            if(!CollectionUtils.isEmpty(strJsonCart)){
                for(String cartStr: strJsonCart){
                    OmsCartItem omsCartItem = JSON.parseObject(cartStr, OmsCartItem.class);
                    String isChecked = omsCartItem.getIsChecked();
                    if(!StringUtils.isEmpty(isChecked) && isChecked.equals("1")){
                        omsOrderItem.setProductPrice(omsCartItem.getPrice());
                        omsOrderItem.setProductName(omsCartItem.getProductName());
                        omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());
                        omsOrderItem.setProductQuantity(omsCartItem.getQuantity().toString());
                        omsOrderItem.setOrderSn(outTradeNumber);/*外部订单号，用来和其他系统进行交互，比如连接Alipay（支付宝）时使用*/
                        omsOrderItem.setProductSkuCode("66666666666666");
                        omsOrderItem.setProductId(omsCartItem.getProductId());
                        omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());
                        omsOrderItem.setProductSn(omsCartItem.getProductSn());
                        /*检验价格*/
                        boolean b = skuService.checkPrice(omsOrderItem.getProductSkuId(), omsOrderItem.getProductPrice());
                        if (b == false) {
                            ModelAndView modelAndView = new ModelAndView("tradeFail");
                            return modelAndView;
                        } else {
                            omsOrderItemList.add(omsOrderItem);
                        }
                        /*校验库存*/
                    }
                }
            }
            omsOrder.setOmsOrderItems(omsOrderItemList);
            /*将订单和订单详情写入数据库  ==== 删除购物车对应的被勾选的商品*/
            orderService.SaveOrderAndDeletCartItem(omsOrder);
            /*删除购物车数据，redis mysql*/
            cartService.delCartByMemberid(memberId);
            /*重定向到支付系统*/
            ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8088/pay/index");
            modelAndView.addObject("outTradeNumber", outTradeNumber);
            modelAndView.addObject("totalAmount", totalAmount);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("fail");
            return modelAndView;
        }
    }
}
