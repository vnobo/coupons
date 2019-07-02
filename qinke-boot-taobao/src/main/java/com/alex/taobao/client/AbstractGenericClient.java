package com.alex.taobao.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;

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
        this.webClient = clientBuilder.build();
        this.objectMapper = objectmapper;
    }
}
