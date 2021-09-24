package com.wft.consumer.configuration;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimplePropertyValueConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    @Bean("connectionFactory")
    public CachingConnectionFactory connectionFactory() {
        // 默认配置
        return new CachingConnectionFactory(host);
    }

    /**
     * template连接参数
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }



}
