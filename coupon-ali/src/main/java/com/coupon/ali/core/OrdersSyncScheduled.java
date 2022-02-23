package com.coupon.ali.core;


import com.coupon.ali.core.service.TaoBaoServer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * rebate-alliance OrderSercice
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Log4j2
@Service
public class OrdersSyncScheduled {

    private final TaoBaoServer taoBaoServer;
    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    public OrdersSyncScheduled(TaoBaoServer taoBaoServer,
                               RabbitMessagingTemplate rabbitMessagingTemplate) {
        this.taoBaoServer = taoBaoServer;
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
    }

    public void beginSyncOrder() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(40);

        while (startTime.isBefore(LocalDateTime.now())) {

            this.startSyncOrder(startTime);

            startTime = startTime.plusMinutes(20);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void asyncOrder() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(4);
        this.startSyncOrder(startTime);
    }

    /**
     * 手动同步订单
     *
     * @param startTime 开始时间
     */

    private void startSyncOrder(LocalDateTime startTime) {

        MultiValueMap<String, Object> requestParams = new LinkedMultiValueMap<>();
        requestParams.set("start_time", startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        requestParams.set("end_time", startTime.plusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        requestParams.set("page_no", "1");
        requestParams.set("query_type", "2");
        this.asyncProgress(requestParams);
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestParams.set("query_type", "3");
        this.asyncProgress(requestParams);

    }


    /**
     * 淘宝订单同步器
     * <p>
     * 获取存在订单,若果存在更新,不存在创建.
     * <p>
     * 每次同步根据内容多少判断它的分页,每次取100 数量.
     * <p>
     * 判断用户是否在同步频率里兑换过口令的商品ID.若果商品ID相同表示是该用户订单.
     * 兑换过口令成功,会将用户ID 放入redis KEY 为 sync:createPwd:{商品ID} 里.
     * <p>
     * 如果是结算订单同步将订单信息更新,改变用户余额.
     * <p>
     * 订单查询类型，创建时间“create_time”，或结算时间“settle_time”
     */
    private void asyncProgress(MultiValueMap<String, Object> requestParams) {


        Mono<JsonNode> jsonNodeMono = this.taoBaoServer.syncOrders(requestParams);

        jsonNodeMono.subscribe(data -> {

            JsonNode results = data.findPath("results");

            if (results.size() == 0) {

                log.debug("请求类型 {} ，淘宝数据获取为空。开始时间 {}", requestParams.get("query_type"),
                        requestParams.get("start_time"));

                return;
            }

            Iterator<JsonNode> orders = results.findValue("publisher_order_dto").elements();

            while (orders.hasNext()) {

                JsonNode node = orders.next();

                log.debug("淘宝 {} 订单同步,订单内容 {}", requestParams.get("query_type"), node);

                this.rabbitMessagingTemplate.convertAndSend("order", node.toString());
            }

            if (data.findValue("has_next").asBoolean()) {
                requestParams.set("jump_type", "1");
                requestParams.set("position_index", data.findValue("position_index").asText());
                this.asyncProgress(requestParams);
            }

        }, error -> log.error("数据请求错误，错误消息：{}", error.getMessage()));

    }


}