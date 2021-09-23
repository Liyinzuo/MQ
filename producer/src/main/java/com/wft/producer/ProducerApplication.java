package com.wft.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Value("${stocks.quote.pattern}")
    private String marketDataRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendMessage")
    public void sendMessage() {
        Message message = MessageBuilder.withBody("chongya".getBytes()).setContentType(MessageProperties.DEFAULT_CONTENT_TYPE).build();
        rabbitTemplate.convertAndSend("app.stock.request", message);
        System.err.println("发送成功");
    }
}
