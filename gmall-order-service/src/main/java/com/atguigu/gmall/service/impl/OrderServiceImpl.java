package com.atguigu.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.OmsOrder;
import com.atguigu.gmall.bean.OmsOrderItem;
import com.atguigu.gmall.cache.service.ICacheService;
import com.atguigu.gmall.mapper.OmsOrderItemMapper;
import com.atguigu.gmall.mapper.OrderMapper;
import com.atguigu.gmall.service.order.OrderService;
import com.atguigu.gmall.util.ActiveMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.Collections;
import java.util.List;

/**
 * @author dalaoban
 * @create 2020-03-21-23:32
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Reference
    ICacheService cacheService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OmsOrderItemMapper omsOrderItemMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Override
    public String checkTradeCode(String memberId, String tradeCode) {
        String tradeKey = "User:" + memberId + ":tradeCode";
        //String tradeCodeFromCache = jedis.get(tradeKey);
        /*lua脚本 防止黑客攻击*/
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Long eval = (Long) cacheService.eval(script, Collections.singletonList(tradeKey), Collections.singletonList(tradeCode));
        /*在这里一个用户可能在多地址登录几乎同时来到这里，这时程序还没来得及执行下面语句就有多个相同的trdaeCode同时返回success，造成OrderController多次提交订单
         * 所以，这里我们可以使用lua脚本，当发现"User:" + memberId + ":tradeCode"，就立即删除*/
        if (eval != null && eval != 0) {
            return "OK";
        } else {
            return "FAIL";
        }
    }

    @Override
    public String genTradeCode(String memberId) {
        return null;
    }

    @Override
    public void SaveOrderAndDeletCartItem(OmsOrder omsOrder) {
        /*保存订单表*/
        orderMapper.insertSelective(omsOrder);
        String orderId = omsOrder.getId();/*主键返回策略==========================================*/
        /*保存订单详情*/
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(orderId);
            omsOrderItemMapper.insertSelective(omsOrderItem);
            /*删除购物车数据*/
            /*cartService.deletByOrderItemProductSkuId(omsOrderItem.getProductSkuId());*/
        }
    }

    @Override
    public OmsOrder getOrderByOutTradeNumber(String outTradeNumber) {
        return null;
    }

    @Override
    public void updataOrder(OmsOrder omsOrder) {
        Connection conn = null;
        Session session = null;
        try {
           conn= activeMQUtil.getConnectionFactory().createConnection();
           session = conn.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        /*订单跟新完毕后 ,给库存发送一个订单已支付的队列*/
        /*支付成功后 引起的系统服务 ，——> 订单服务更新 ，——> （当前）库存服务 ，——> 物流服务*/
        /*=================================这里我们将引入分布式事务消息中间件ActiveMQ================================*/
        /*调用ActiveMQ发送支付成功的消息*/
        Queue order_payed_queue = null;
        try {
            Example example = new Example(OmsOrder.class);
            example.createCriteria().andEqualTo("orderSn" , omsOrder.getOrderSn());
            omsOrder.setStatus("1");/*设置成已支付的状态*/
            /*更新订单状态*/
            orderMapper.updateByExampleSelective(omsOrder,example);
            order_payed_queue = session.createQueue("ORDER_PAYED_QUEUE");
            MessageProducer producer = session.createProducer(order_payed_queue);
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("out_trade_no",omsOrder.getOrderSn());
            producer.send(mapMessage);/*发送消息*/
            session.commit();
        } catch (JMSException e) {
            try {
                /*更新失败回滚消息*/
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
