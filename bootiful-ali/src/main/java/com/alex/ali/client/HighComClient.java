package com.alex.ali.client;

import cn.hutool.json.JSONUtil;
import com.alex.ali.config.AliProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

/**
 * boot-cool-alliance HighComClient
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Log4j2
@Service
public class HighComClient extends BaseClient {

    private final String appKey;
    private final String sid;

    public HighComClient(WebClient.Builder builder,
                         AliProperties aliProperties) {
        super(builder.baseUrl(aliProperties.getHighApi()).build());
        this.appKey = aliProperties.getHighKey();
        this.sid = aliProperties.getHighUid();
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
            log.error("Get goods id response getTBPwd body convert to json error, msg " + e.getMessage());
            return null;
        }
    }

}
