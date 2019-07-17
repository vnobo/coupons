package com.alex.taobao.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.codec.json.Jackson2SmileDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.MediaType.*;

/**
 * qinke-coupons AbstractGenericClient
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/6/18
 */
@Log4j2
public abstract class AbstractGenericClient {

    protected WebClient webClient;
    protected ObjectMapper objectMapper;

    protected AbstractGenericClient(WebClient.Builder clientBuilder, ObjectMapper objectmapper) {

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    configurer.customCodecs().decoder(new Jackson2JsonDecoder(objectmapper,ALL));
                    configurer.customCodecs().encoder(new Jackson2JsonEncoder(objectmapper,ALL));
                })
                .build();
        this.webClient = clientBuilder.exchangeStrategies(strategies).build();

        this.objectMapper = objectmapper;
    }

}
