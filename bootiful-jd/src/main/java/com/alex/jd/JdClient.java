package com.alex.wx.alliance.jd;

import cn.hutool.json.JSONUtil;
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
public class JdClient extends AbstractGenericClient {

    private final String appKey;
    private final String appSecret;
    private final String signMethod;


    public JdClient(WebClient webClient, ObjectMapper objectMapper, String appKey, String appSecret, String signMethod) {
        super(webClient, objectMapper);
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.signMethod = signMethod;
    }

    public JsonNode postForEntity(MultiValueMap<String, String> params) {
        params.set("app_key", this.appKey);
        params.set("v", "1.0");
        params.set("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.set("format", "json");

        params.set("sign_method", this.signMethod);
        params.remove("sign");
        String sign = SignRequestUtils.signTopRequest(params, this.appSecret, this.signMethod);
        params.set("sign", sign);

        String result = this.webClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .syncBody(params)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new JdRequestException(500,
                        "京东API请求失败!msg: " + clientResponse.toString())))
                .bodyToMono(String.class)
                .block();
        try {

            if (!JSONUtil.isJson(result)) {
                throw new JdRequestException(500, "Get response body type no json format.");
            }

            return this.objectMapper.readValue(result, JsonNode.class);
        } catch (IOException e) {
            throw new JdRequestException(500, "Get response body convert to json error, msg " + e.getMessage());
        }
    }

    public class JdRequestException extends RestServerException {

        public JdRequestException(int code, String msg) {
            super(code, msg);
        }
    }
}
