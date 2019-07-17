package com.alex.taobao.service;

import cn.hutool.core.util.ObjectUtil;
import com.alex.taobao.model.Order;
import com.alex.taobao.model.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * rebate-alliance OrderSercice
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Log4j2
@Service
public class OrderService {

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final TaoBaoServer taoBaoServer;

    public OrderService(ObjectMapper objectMapper,
                        OrderRepository orderRepository,
                        TaoBaoServer taoBaoServer) {
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
        this.taoBaoServer = taoBaoServer;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Async
    public Future<Order> asyncSave(Order order) {
        return new AsyncResult<>(save(order));
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
    public void asyncProgress(int page, LocalDateTime startTime, int span, int queryType) {

        JsonNode jsonNode = this.taoBaoServer.syncOrders(page, startTime, span, queryType);

        JsonNode results = jsonNode.findPath("results");

        if (results.size() == 0) {
            return;
        }
        Iterator<JsonNode> orders = results.findValue("publisher_order_dto").elements();

        while (orders.hasNext()) {

            JsonNode node = orders.next();

            log.info("淘宝 {} 订单同步,订单内容 {}", queryType, node);

            Order order = this.objectMapper.convertValue(node, Order.class);
            order.setAsyncTime(LocalDateTime.now());
            order.setType(1);
            order.getExtend().set("trade", node);
            this.asyncSave(order);
        }

        if (jsonNode.findValue("has_next").asBoolean()) {
            this.asyncProgress(page + 1, startTime, span, queryType);
        }
    }


}
