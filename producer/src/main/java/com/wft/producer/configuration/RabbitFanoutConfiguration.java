package com.wft.producer.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lyz
 * @date: 2021/9/24 11:16
 */
@Configuration
public class RabbitFanoutConfiguration extends AbstractStockAppRabbitConfiguration {

    @Bean
    public Queue cQueue() {
        return new Queue("app.fanout.c", true);
    }

    @Bean
    public Queue dQueue() {
        return new Queue("app.fanout.d", true);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("app.exchange.fanout");
    }

    @Bean
    public Binding fanoutC() {
        return BindingBuilder.bind(cQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutD() {
        return BindingBuilder.bind(dQueue()).to(fanoutExchange());
    }

}
