package com.wft.consumer.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: lyz
 * @date: 2021/9/23 17:50
 */
@Component
public class RabbitListenerService {

    @RabbitListener(queues = {"app.stock.request"})
    public void direct(Map message) {
        System.err.println("{app.stock.request} -> message:" + message);
    }

    @RabbitListener(queues = {"app.topic.a"})
    public void topicA(Map message) {
        System.err.println("{app.topic.a} -> message:" + message);
    }

    @RabbitListener(queues = {"app.topic.b"})
    public void topicB(Map message) {
        System.err.println("{app.topic.b} -> message:" + message);
    }

    @RabbitListener(queues = {"app.fanout.c"})
    public void topicC(Map message) {
        System.err.println("{app.topic.c} -> message:" + message);
    }

    @RabbitListener(queues = {"app.fanout.d"})
    public void topicD(Map message) {
        System.err.println("{app.topic.d} -> message:" + message);
    }


}
