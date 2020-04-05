package com.atguigu.gmall.service.order;

import com.atguigu.gmall.bean.OmsOrder;

/**
 * @author dalaoban
 * @create 2020-03-21-22:46
 */
public interface OrderService {

    String checkTradeCode(String memberId, String tradeCode);

    String genTradeCode(String memberId);

    void SaveOrderAndDeletCartItem(OmsOrder omsOrder);

    OmsOrder getOrderByOutTradeNumber(String outTradeNumber);

    void updataOrder(OmsOrder omsOrder);
}
