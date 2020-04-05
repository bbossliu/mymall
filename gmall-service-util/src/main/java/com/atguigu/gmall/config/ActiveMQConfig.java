package com.atguigu.gmall.config;

import com.atguigu.gmall.util.ActiveMQUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

/**
 * @author dalaoban
 * @create 2020-03-22-19:27
 */
@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url:disabled}")
    String brokerURL;

    @Value("${activemq.listener.enable:disabled}")
    String listenerEnable;


    @Bean
    public ActiveMQUtil getActiveMQUtil(){
        if(brokerURL.equals("disable")){
            return null;
        }
        ActiveMQUtil activeMQUtil = new ActiveMQUtil();
        activeMQUtil.init(brokerURL);
        return activeMQUtil;
    }

    /*定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂*/
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        if (!listenerEnable.equals("true")){
            return null;
        }
        factory.setConnectionFactory(activeMQConnectionFactory);
        /*设置并发数*/
        factory.setConcurrency("5");
        /*重新连接间隔时间*/
        factory.setRecoveryInterval(5000L);
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
        return activeMQConnectionFactory;
    }
}
