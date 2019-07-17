package com.alex.taobao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * boot-cool-alliance TbKeConfiguration
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Configuration
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties(TaoBaoProperties.class)
public class TaoBaoConfiguration implements WebFluxConfigurer {

    private final ObjectMapper objectMapper;

    public TaoBaoConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(
                new Jackson2JsonEncoder(objectMapper)
        );

        configurer.defaultCodecs().jackson2JsonDecoder(
                new Jackson2JsonDecoder(objectMapper)
        );
    }
}
