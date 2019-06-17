package com.alex.wxmp.alliance;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * boot-cool-alliance AbstractGenericClient
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public abstract class AbstractGenericClient {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final ObjectMapper objectMapper;

    protected final WebClient webClient;

    protected AbstractGenericClient(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        if (ObjectUtil.isNull(objectMapper)) {
            this.objectMapper = new ObjectMapper();
        } else {
            this.objectMapper = objectMapper;
        }
    }

}
