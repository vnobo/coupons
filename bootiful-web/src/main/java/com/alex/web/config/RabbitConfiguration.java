package com.alex.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.mspbots.sync.config.RabbitConfigurer
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/29
 */
@Configuration
public class RabbitConfiguration extends RabbitAutoConfiguration {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Bean
    public void declareTicketAi() {
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
    }
}
