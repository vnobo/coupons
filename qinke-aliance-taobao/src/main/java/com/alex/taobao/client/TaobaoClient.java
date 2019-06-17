package com.alex.taobao.client;

import cn.hutool.json.JSONUtil;
import com.alex.taobao.SignRequestUtils;
import com.alex.taobao.TaoBaoRestException;
import com.alex.taobao.config.TaoBaoProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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
@Log4j2
@Service
public class TaobaoClient extends AbstractGenericClient {

    private final String appKey;
    private final String appSecret;
    private final String signMethod;

    public TaobaoClient(WebClient.Builder clientBuilder,
                        ObjectMapper objectMapper,
                        TaoBaoProperties taoBaoProperties) {
        super(clientBuilder.baseUrl(taoBaoProperties.getApiUrl()), objectMapper);
        this.appKey = taoBaoProperties.getAppKey();
        this.appSecret = taoBaoProperties.getAppSecret();
        this.signMethod = taoBaoProperties.getSignMethod();

    }

    public JsonNode postForEntity(MultiValueMap<String, String> params) {
        params.set("app_key", this.appKey);
        params.set("v", "2.0");
        params.set("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.set("format", "json");
        log.debug("sign request params,params json is: " + params.toString());
        params.set("sign_method", this.signMethod);
        params.remove("sign");
        String sign = SignRequestUtils.signTopRequest(params, this.appSecret, this.signMethod);
        log.debug("sign request params,sign str is: " + sign);
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
                log.error("Get response body type no json format.");
                return null;
            }
            return this.objectMapper.readValue(result, JsonNode.class);
        } catch (IOException e) {
            log.error("Get response body convert to json error, msg " + e.getMessage());
            return null;
        }
    }

    public class TaobaoRequestException extends TaoBaoRestException {
        TaobaoRequestException(int code, String msg) {
            super(code, msg);
        }
    }
}
