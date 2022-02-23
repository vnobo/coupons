package com.coupon.ali.core.client;

import com.coupon.ali.BaseGenericService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * qinke-coupons BaseClient
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/6/18
 */
@Log4j2
public abstract class BaseClient extends BaseGenericService {

    protected WebClient webClient;

    protected BaseClient(WebClient clientBuilder) {
        this.webClient = clientBuilder;
    }

}