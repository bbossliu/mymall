package com.atguigu.gmall.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.OmsOrder;
import com.atguigu.gmall.mapper.OrderMapper;
import com.atguigu.gmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.jms.JMSException;
import javax.jms.MapMessage;

/**
 * @author dalaoban
 * @create 2020-04-04-15:11
 */
@Component
public class OrderServiceMqListener {

    @Reference
    OrderService orderService;

    @JmsListener(destination = "PAYMENT_SUCCESS_QUEUE",containerFactory = "jmsQueueListener")
    public void consumPaymentResult(MapMessage mapMessage) throws JMSException {
        /*在PaymentServiceImpl中支付成功后成功更新了paymentInfo的支付状态，发送消息给订单，让其更新支付状态（status）*/
        /*更新订单状态业务*/
        String out_trade_no = mapMessage.getString("out_trade_no");
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setOrderSn(out_trade_no);
        /*调用更新订单状态方法，和引起下一步骤的运行*/
        orderService.updataOrder(omsOrder);
    }
}
