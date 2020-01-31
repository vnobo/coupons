package com.alex.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * com.mspbots.sync.config.RabbitConfigurer
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/29
 */
@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {
/*
    private final AmqpAdmin amqpAdmin;

    @Bean
    public void declareQueue() {
        Exchange coupons = ExchangeBuilder.directExchange("com.coupons.core").build();
        this.amqpAdmin.declareExchange(coupons);

        Queue tickets = QueueBuilder.durable("order").build();
        this.amqpAdmin.declareQueue(tickets);

        this.amqpAdmin.declareBinding(BindingBuilder.bind(tickets)
                .to(coupons).with("order-save").noargs());
    }

    @Bean
    public MessageConverter rabbitMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }*/
}
