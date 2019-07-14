package com.alex.wxmp.alliance.jd.config;

import com.alex.wxmp.alliance.jd.JdClient;
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
@EnableConfigurationProperties(JdProperties.class)
public class JdConfiguration {

    private JdProperties properties;

    private ObjectMapper objectMapper;

    @Autowired
    public JdConfiguration(JdProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Bean
    @Primary
    public JdClient jdClient(WebClient.Builder builder) {

        WebClient webClient = builder.baseUrl(this.properties.getApiUrl()).build();

        return new JdClient(webClient, this.objectMapper,
                this.properties.getAppKey(),
                this.properties.getAppSecret(),
                this.properties.getSignMethod());
    }


}
