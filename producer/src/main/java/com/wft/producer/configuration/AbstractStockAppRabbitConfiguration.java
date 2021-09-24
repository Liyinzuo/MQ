package com.wft.producer.configuration;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lyz
 * @date: 2021/9/23 16:12
 */
@Configuration
public abstract class AbstractStockAppRabbitConfiguration {

    @Value("${spring.rabbitmq.host}")
    public String host;

    /**
     * 配置连接名
     * @return
     */
    @Bean
    public SimplePropertyValueConnectionNameStrategy cns() {
        return new SimplePropertyValueConnectionNameStrategy("spring.application.name");
    }


    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * 配置connect
     * @return
     */
    @Bean
    public CachingConnectionFactory connectionFactory() {
        // 默认配置
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        // 消息 -> exchange失败调用
        cachingConnectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        // 确认消息发送到队列
        cachingConnectionFactory.setPublisherReturns(true);
        return cachingConnectionFactory;
    }

    /**
     * template连接参数
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        // 触发ReturnsCallback回调函数开关
        rabbitTemplate.setMandatory(true);
        // SERVER-EXCHANGE
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.err.println("ReturnsCallback:    " + "消息:" + returnedMessage.getMessage());
                System.err.println("ReturnsCallback:    " + "回应码:" + returnedMessage.getReplyCode());
                System.err.println("ReturnsCallback:    " + "回应消息:" + returnedMessage.getReplyText());
                System.err.println("ReturnsCallback:    " + "交换机:" + returnedMessage.getExchange());
                System.err.println("ReturnsCallback:    " + "路由:" + returnedMessage.getRoutingKey());
            }
        });
        //EXCHANGE-QUEUE
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+b);
                System.out.println("ConfirmCallback:     "+"原因："+s);
            }
        });
        return rabbitTemplate;
    }




}
