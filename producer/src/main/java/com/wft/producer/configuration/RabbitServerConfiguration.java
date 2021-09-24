package com.wft.producer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lyz
 * @date: 2021/9/23 16:34
 */
@Configuration
public class RabbitServerConfiguration extends AbstractStockAppRabbitConfiguration {



    @Value("${stocks.quote.pattern}")
    private String marketDataRoutingKey;


    @Bean
    public DirectExchange marketDataExchange() {
        return new DirectExchange("app.stock.marketdirect");
    }

    @Bean
    public DirectExchange noque() {
        return new DirectExchange("app.stock.noque");
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("app.stock.request");
    }

    /**
     * Binds to the market data exchange.
     * Interested in any stock quotes
     * that match its routing key.
     */
    @Bean
    public Binding marketDataBinding() {
        return BindingBuilder
                .bind(requestQueue())
                .to(marketDataExchange())
                .with(marketDataRoutingKey);
    }

}
