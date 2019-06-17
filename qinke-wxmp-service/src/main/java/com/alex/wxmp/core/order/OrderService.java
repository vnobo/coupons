package com.alex.wxmp.core.order;

import cn.hutool.core.util.ObjectUtil;
import com.alex.wxmp.AbstractGenericService;
import com.alex.wxmp.alliance.taobao.TaoBaoServer;
import com.alex.wxmp.core.Customer.CustomerService;
import com.alex.wxmp.core.Customer.beans.Customer;
import com.alex.wxmp.core.order.beans.Order;
import com.alex.wxmp.core.order.beans.OrderRepository;
import com.alex.wxmp.core.wallet.WalletService;
import com.fasterxml.jackson.databind.JsonNode;
import com.querydsl.core.types.Predicate;
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
@Service
public class OrderService extends AbstractGenericService {

    private OrderRepository orderRepository;

    private TaoBaoServer taoBaoServer;
    private WalletService walletService;
    private CustomerService customerService;

    public OrderService(OrderRepository orderRepository,
                        TaoBaoServer taoBaoServer,
                        WalletService walletService,
                        CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.taoBaoServer = taoBaoServer;
        this.walletService = walletService;
        this.customerService = customerService;
    }

    public Order loadByTidAndType(String tid, int type) {
        Optional<Order> optionalOrder = this.orderRepository.findByTradeIdAndType(tid, type);
        return optionalOrder.orElse(null);
    }

    public Page<Order> loadPageOrder(Predicate predicate, Pageable pageable) {
        return this.orderRepository.findAll(predicate, pageable);
    }

    public Order save(Order order) {
        return orderRepository.saveAndFlush(order);
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
    @Async
    public void asyncProgress(int page, LocalDateTime startTime, String queryType, int span) {

        int totalNum = 0;

        JsonNode jsonNode = this.taoBaoServer.syncOrders(startTime, page, 1, queryType, span);

        Iterator<JsonNode> orders = jsonNode.elements();

        while (orders.hasNext()) {

            JsonNode node = orders.next();

            totalNum++;

            this.logger.info("淘宝 {} 订单同步,同步编号 {} ,订单内容 {}", queryType, totalNum, node);

            Order order = this.objectMapper.convertValue(node, Order.class);
            if (queryType.equalsIgnoreCase("settle_time")) {
                Order dbOrder = this.loadByTidAndType(order.getTradeId(), 1);
                if (ObjectUtil.isNotNull(dbOrder)) {
                    order.setId(dbOrder.getId());
                    order.setOpenId(dbOrder.getOpenId());
                    // 结算订单同步
                    if (order.getTkStatus() == 3 && dbOrder.getTkStatus() != 3) {
                        this.walletService.plusBalance(order.getTradeId(), order.getOpenId(), order.getTotalCommissionFee());
                    }
                } else {
                    this.logger.error("同步结算订单失败,原订单不存在,无法结算,订单详细信息: {}", order);
                }
            } else {
                Customer customer = this.customerService.loadByPid(order.getAdzoneId());
                if (ObjectUtil.isNotNull(customer)) {
                    order.setOpenId(customer.getOpenId());
                } else {
                    this.logger.error("order adZone id is error ,adZoneId is {}.this system not build user!", order.getAdzoneId());
                }
            }
            order.setAsyncTime(LocalDateTime.now());
            order.setType(1);
            order.getExtend().withArray("nodes").insert(0, node.deepCopy());
            this.asyncSave(order);
        }

        if (totalNum == 100) {
            this.asyncProgress(page + 1, startTime, queryType, span);
        }
    }


}
