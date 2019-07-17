package com.alex.taobao.client;

import com.alex.taobao.SignRequestUtils;
import com.alex.taobao.TaoBaoRestException;
import com.alex.taobao.config.TaoBaoProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.MediaType.ALL;

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
        super(clientBuilder.baseUrl(taoBaoProperties.getApiUrl())
                .exchangeStrategies((ExchangeStrategies) ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer->{
                    clientCodecConfigurer.customCodecs().encoder(new Jackson2JsonEncoder(objectMapper, ALL));
                    clientCodecConfigurer.customCodecs().decoder(new Jackson2JsonDecoder(objectMapper, ALL));
                })), objectMapper);
        this.appKey = taoBaoProperties.getAppKey();
        this.appSecret = taoBaoProperties.getAppSecret();
        this.signMethod = taoBaoProperties.getSignMethod();

    }

    public Mono<JsonNode> postForEntity(MultiValueMap<String, String> params) {

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

        return this.webClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .syncBody(params)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new TaobaoRequestException(500, "淘宝API请求失败!msg: " + clientResponse.toString())))
                .bodyToMono(JsonNode.class);

    }

    public class TaobaoRequestException extends TaoBaoRestException {
        TaobaoRequestException(int code, String msg) {
            super(code, msg);
        }
    }
}
