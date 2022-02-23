package com.coupon.ali.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
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

    private final AmqpAdmin amqpAdmin;

    @Bean
    public MessageConverter rabbitMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


}