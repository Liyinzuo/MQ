package com.wft.consumer.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author: lyz
 * @date: 2021/9/23 17:50
 */
@Service
@Lazy(value = false)
public class RabbitListenerService {

    @RabbitListener(queues = {"app.stock.request"})
    public void queRom(String message) {
        System.err.println("message:" + message);
    }


}
