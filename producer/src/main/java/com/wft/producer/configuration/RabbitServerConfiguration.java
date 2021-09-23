package com.wft.producer.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lyz
 * @date: 2021/9/23 16:34
 */
@Configuration
public class RabbitServerConfiguration extends AbstractStockAppRabbitConfiguration {

    @Bean
    public Queue defaultQueue() {
        return new Queue("app.stock.request");
    }

    @Value("${stocks.quote.pattern}")
    private String marketDataRoutingKey;


    @Bean
    public Queue marketDataQueue() {
        // autodelete exclusive独占
        return rabbitAdmin().declareQueue();
    }

    /**
     * Binds to the market data exchange.
     * Interested in any stock quotes
     * that match its routing key.
     */
    @Bean
    public Binding marketDataBinding() {
        return BindingBuilder
                .bind(defaultQueue())
                .to(marketDataExchange())
                .with(marketDataRoutingKey);
    }

}
