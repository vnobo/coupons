package com.alex.bootiful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * qinke-coupons com.alex.bootiful.config.WebMvcConfigurer
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/25
 */
@Configuration
@EnableRedisHttpSession
public class WebMvcConfigurer {

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }
}
