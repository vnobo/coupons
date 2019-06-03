package com.alex.alliance.taobao;

import cn.hutool.json.JSONUtil;
import com.alex.RestServerException;
import com.alex.alliance.AbstractGenericClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * boot-cool-alliance HighComClient
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public class HighComClient extends AbstractGenericClient {

    private final String appKey;
    private final String sid;
    private WebClient webClient;

    public HighComClient(WebClient.Builder builder,
                         ObjectMapper objectMapper,
                         String appKey, String sid) {
        // webClient设置
        super(builder.build(), objectMapper);
        this.webClient = builder.build();
        this.appKey = appKey;
        this.sid = sid;
    }

    /**
     * 淘口令解析商品ID
     * 有可能再淘宝客商品库不存在
     */
    public String getGoodsId(String content) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("appkey", this.appKey);
        params.set("sid", this.sid);
        params.set("content", content);
        String result = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("open_shangpin_id.ashx")
                        .queryParams(params).build())
                .retrieve().bodyToMono(String.class).block();
        try {

            if (!JSONUtil.isJson(result)) {
                return null;
            }
            return this.objectMapper.readValue(result, JsonNode.class).findPath("item_id").asText();

        } catch (IOException e) {
            this.logger.error("Get goods id response getTBPwd body convert to json error, msg " + e.getMessage());
            return null;
        }
    }

}
