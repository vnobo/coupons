package com.alex.taobao;


import cn.hutool.json.JSONUtil;
import com.alex.taobao.exceptions.OrderSyncProgressException;
import com.alex.taobao.service.TaoBaoServer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;

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


    //@EventListener(ApplicationReadyEvent.class)
    public void manualSyncOrder() {
        LocalDateTime startTime = LocalDateTime.of(2019, 7, 20, 0, 0);
        this.manualSyncOrder(startTime);
    }

    /**
     * 手动同步订单
     *
     * @param startTime 开始时间
     */
    @Async
    public void manualSyncOrder(LocalDateTime startTime) {

        while (startTime.isBefore(LocalDateTime.now())) {
            this.asyncProgress(1, startTime, 20, 2);
            this.asyncProgress(1, startTime, 20, 3);
            startTime = startTime.plusMinutes(20);
        }
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void asyncOrder() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(4);
        this.asyncProgress(1, startTime, 2, 2);
        this.asyncProgress(1, startTime, 2, 3);

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
    private void asyncProgress(int page, LocalDateTime startTime, int span, int queryType) {

        Mono<JsonNode> jsonNodeMono = this.taoBaoServer.syncOrders(page, startTime, span, queryType);

        jsonNodeMono.subscribe(data -> {
            JsonNode results = data.findPath("results");

            if (results.size() == 0) {
                log.info("淘宝数据获取为空。开始时间 {}", startTime);
                return;
            }
            Iterator<JsonNode> orders = results.findValue("publisher_order_dto").elements();

            while (orders.hasNext()) {

                JsonNode node = orders.next();

                log.info("淘宝 {} 订单同步,订单内容 {}", queryType, node);

                this.rabbitMessagingTemplate.convertAndSend("order", node.toString());
            }

            if (results.findValue("has_next").asBoolean()) {
                this.asyncProgress(page + 1, startTime, span, queryType);
            }
        }, error -> log.error("数据请求错误，错误消息：{}", error.getMessage()));

    }


}
