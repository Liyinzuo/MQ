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

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Value("${stocks.quote.pattern}")
    private String marketDataRoutingKey;

    @Value("${stocks.topic.pattern}")
    private String topicRouting;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendDirect")
    public void sendDirect() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.stock.marketdirect", marketDataRoutingKey, message);
        System.err.println("发送成功");
    }

    @GetMapping("sendTopic")
    public void sendTopic() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.exchange.topic", topicRouting, message);
        System.err.println("发送成功");
    }

    @GetMapping("sendFanout")
    public void sendFanout() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.exchange.fanout", null, message);
        System.err.println("发送成功");
    }

    @GetMapping("noexchange")
    public void noexchange() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.exchange.noexchange", null, message);
        System.err.println("发送成功");
    }

    @GetMapping("noque")
    public void noque() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.stock.noque", "testque", message);
        System.err.println("发送成功");
    }

}
