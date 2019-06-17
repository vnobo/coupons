package com.alex.wxmp.alliance.taobao;

import cn.hutool.json.JSONUtil;
import com.alex.wxmp.RestServerException;
import com.alex.wxmp.alliance.AbstractGenericClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * boot-cool-alliance TaobaoClient
 * Created by 2019-02-15
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public class TaobaoClient extends AbstractGenericClient {

    private final String appKey;
    private final String appSecret;
    private final String signMethod;


    public TaobaoClient(WebClient webClient, ObjectMapper objectMapper, String appKey, String appSecret, String signMethod) {
        super(webClient, objectMapper);
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.signMethod = signMethod;
    }

    public JsonNode postForEntity(MultiValueMap<String, String> params) {

        params.set("app_key", this.appKey);
        params.set("v", "2.0");
        params.set("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.set("format", "json");

        logger.debug("sign request params,params json is: " + params.toString());
        params.set("sign_method", this.signMethod);
        params.remove("sign");
        String sign = SignRequestUtils.signTopRequest(params, this.appSecret, this.signMethod);
        logger.debug("sign request params,sign str is: " + sign);
        params.set("sign", sign);

        String result = this.webClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .syncBody(params)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new TaobaoRequestException(500, "淘宝API请求失败!msg: " + clientResponse.toString())))
                .bodyToMono(String.class)
                .block();
        try {

            if (!JSONUtil.isJson(result)) {
                this.logger.error("Get response body type no json format.");
                return null;
            }
            return this.objectMapper.readValue(result, JsonNode.class);
        } catch (IOException e) {
            this.logger.error("Get response body convert to json error, msg " + e.getMessage());
            return null;
        }
    }

    public class TaobaoRequestException extends RestServerException {
        TaobaoRequestException(int code, String msg) {
            super(code, msg);
        }
    }
}
