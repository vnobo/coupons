package com.coupon.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * com.mspbots.sync.config.RabbitConfigurer
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/29
 */
@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

    private final AmqpAdmin amqpAdmin;

    @PostConstruct
    public void declareQueue() {
        Exchange coupons = ExchangeBuilder.directExchange("com.coupons.core").build();
        this.amqpAdmin.declareExchange(coupons);

        Queue orders = QueueBuilder.durable("orders").build();
        this.amqpAdmin.declareQueue(orders);

        this.amqpAdmin.declareBinding(BindingBuilder.bind(orders)
                .to(coupons).with("order-save").noargs());
    }

}