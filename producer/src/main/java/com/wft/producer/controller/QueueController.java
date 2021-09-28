package com.wft.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lyz
 * @date: 2021/9/28 15:39
 */
@RestController
public class QueueController {

    private static Logger LOG = LoggerFactory.getLogger(QueueController.class);

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
        LOG.info("发送成功");
    }

    @GetMapping("sendTopic")
    public void sendTopic() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.exchange.topic", topicRouting, message);
        LOG.info("发送成功");
    }

    @GetMapping("sendFanout")
    public void sendFanout() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.exchange.fanout", null, message);
        LOG.info("发送成功");
    }

    @GetMapping("noexchange")
    public void noexchange() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.exchange.noexchange", null, message);
        LOG.info("发送成功");
    }

    @GetMapping("noque")
    public void noque() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", 123);
        rabbitTemplate.convertAndSend("app.stock.noque", null, message);
        LOG.info("发送成功");
    }

}
