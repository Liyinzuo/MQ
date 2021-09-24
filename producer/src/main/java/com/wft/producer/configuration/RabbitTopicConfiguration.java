package com.wft.producer.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lyz
 * @date: 2021/9/24 10:37
 */
@Configuration
public class RabbitTopicConfiguration extends AbstractStockAppRabbitConfiguration{

    @Value("${stocks.topic.pattern}")
    private String topicRouting;

    @Bean
    public Queue aQueue() {
        return new Queue("app.topic.a", true);
    }

    @Bean
    public Queue bQueue() {
        return new Queue("app.topic.b", true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("app.exchange.topic");
    }

    @Bean
    public Binding topicBindinga() {
        return BindingBuilder.bind(aQueue()).to(topicExchange()).with(topicRouting);
    }

    @Bean
    public Binding topicBindingb() {
        return BindingBuilder.bind(bQueue()).to(topicExchange()).with(topicRouting);
    }
}
