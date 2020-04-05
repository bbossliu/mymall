package com.atguigu.gmall;

import com.atguigu.gmall.util.ActiveMQUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * @author dalaoban
 * @create 2020-03-22-21:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Resource
    ActiveMQUtil activeMQUtil;

    @org.junit.Test
    public void test1(){
        try {
            Connection connection = activeMQUtil.getConnectionFactory().createConnection();
            connection.start();
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
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Queue queue = session.createQueue("testOne");
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("第一次测试消息队列");
            producer.send(textMessage);
            session.commit();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void test2(){
        try {
            Connection connection = activeMQUtil.getConnectionFactory().createConnection();
            connection.start();
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
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("testOne");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new MessageListener(){

                @Override
                public void onMessage(Message message) {
                    if(message instanceof  TextMessage){
                        String text = null;
                        try {
                            text = ((TextMessage) message).getText();
                            System.out.println(text);
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
