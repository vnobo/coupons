package com.alex.alliance.taobao.config;

import com.alex.alliance.taobao.HighComClient;
import com.alex.alliance.taobao.TaobaoClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * boot-cool-alliance TbKeConfiguration
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Configuration
@EnableConfigurationProperties(TbKeProperties.class)
public class TbKeConfiguration {

    private TbKeProperties properties;

    private ObjectMapper objectMapper;

    @Autowired
    public TbKeConfiguration(TbKeProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Bean
    @Primary
    public TaobaoClient taobaoClient(WebClient.Builder builder) {

        WebClient webClient = builder.baseUrl(this.properties.getApiUrl()).build();

        return new TaobaoClient(webClient, this.objectMapper,
                this.properties.getAppKey(),
                this.properties.getAppSecret(),
                this.properties.getSignMethod());
    }

    @Bean
    @Primary
    public HighComClient HighComClient(WebClient.Builder builder) {
        return new HighComClient(builder.baseUrl(this.properties.getHighAPI()),
                this.objectMapper,
                this.properties.getHighKey(), this.properties.getHighUid());
    }

}
