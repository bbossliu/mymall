package com.atguigu.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.atguigu.gmall.bean.PaymentInfo;
import com.atguigu.gmall.mapper.PaymentInfoMapper;
import com.atguigu.gmall.service.payment.PaymentService;
import com.atguigu.gmall.util.ActiveMQUtil;
import com.google.common.collect.Maps;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dalaoban
 * @create 2020-03-22-13:54
 */
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentInfoMapper paymentInfoMapper;

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Override
    public void savePaymentInfo(PaymentInfo paymentInfo) {
        paymentInfoMapper.insertSelective(paymentInfo);
    }

    @Override
    public void updatePayment(PaymentInfo paymentInfo) {
        /*=====================================================================================================================================*/
        /*支付成功后 引起的系统服务 ，——> （当前）订单服务更新 ，——> 库存服务 ，——> 物流服务*/
        /*这里我们将引入分布式事务消息中间件ActiveMQ*//*调用ActiveMQ发送支付成功的消息*/
        /*=====================================================================================================================================*/
        Session session =null;
        Connection conn=null;
        try {
             conn = activeMQUtil.getConnectionFactory().createConnection();
            /*
            *   自动签收
            *   int AUTO_ACKNOWLEDGE = 1;
            *   手动签收
                int CLIENT_ACKNOWLEDGE = 2;
                延迟提交
                int DUPS_OK_ACKNOWLEDGE = 3;
                事务
                int SESSION_TRANSACTED = 0;
            * */
             session = conn.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            /*======================================================================================================================================*/
            /*进行支付更新的幂等性检查操作在updatePayment方法里面，防止paymentController 与 PaymentServiceMqListener 一起重复更新*/
            /*======================================================================================================================================*/
            PaymentInfo paymentInfo1 = new PaymentInfo();
            paymentInfo1.setOrderSn(paymentInfo.getOrderSn());
            PaymentInfo paymentInfoResult = paymentInfoMapper.selectOne(paymentInfo1);
            if (paymentInfoResult != null && paymentInfoResult.getPaymentStatus().equals("已付款")) {
                /*说明在paymentController 与 PaymentServiceMqListener里面其中一个已经执行了更新操作*/
                return;
            } else {
                Example example = new Example(PaymentInfo.class);
                example.createCriteria().andEqualTo("orderId", paymentInfo.getOrderId());
                /*更新订单状态*/
                paymentInfoMapper.updateByExampleSelective(paymentInfo,example);
                Queue payment_success_queue = session.createQueue("PAYMENT_SUCCESS_QUEUE");
                MessageProducer producer = session.createProducer(payment_success_queue);
                MapMessage mapMessage = session.createMapMessage();
                /*set外部订单号到map中*/
                mapMessage.setString("out_trade_no",paymentInfo.getOrderSn());
                producer.send(mapMessage);
                session.commit();
            }
        } catch (JMSException e) {
            try {
                /*更新失败，回滚*/
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

    @Override
    public void sendDelayPaymentResultCheckQueue(String outTradeNumber, int count) {
        Connection conn=null;
        Session session=null;
        try {
            conn = activeMQUtil.getConnectionFactory().createConnection();
            session= conn.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            Queue payment_check_queue = session.createQueue("PAYMENT_CHECK_QUEUE");
            MessageProducer producer = session.createProducer(payment_check_queue);
            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("out_trade_no",outTradeNumber);
            /*设置检查次数*/
            mapMessage.setInt("count",count);
            mapMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 1000 * 30);/*加入延迟时间*/
            producer.send(mapMessage);/*发送消息*/
            session.commit();
        } catch (JMSException e) {
            try {
                /*发生异常回滚消息*/
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }


    }

    /*主动检查支付状态*/
    @Override
    public Map<String, Object> checkAlipayPayment(String out_trade_no) {
        Map<String, Object> resultMap  = Maps.newHashMapWithExpectedSize(4);

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("out_trade_no", out_trade_no);
        request.setBizContent(JSON.toJSONString(requestMap));
        AlipayTradeQueryResponse response=null;
        try {
             response = alipayClient.execute(request);
            if (response!=null && response.isSuccess()) {
                System.out.println("调用成功");
                resultMap.put("out_trade_no", response.getOutTradeNo());
                resultMap.put("trade_no", response.getTradeNo());
                resultMap.put("trade_status", response.getTradeStatus());
                resultMap.put("callback_content", response.getMsg());
            } else {
                System.out.println("有可能交易未创建调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
