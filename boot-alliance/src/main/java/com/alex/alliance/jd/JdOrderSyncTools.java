package com.alex.alliance.jd;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alex.AbstractGenericService;
import com.alex.core.order.OrderService;
import com.alex.core.order.beans.Order;
import com.alex.core.wallet.WalletService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;

@Component
public class JdOrderSyncTools extends AbstractGenericService {

    private JdServer jdServer;
    private OrderService orderService;
    private WalletService walletService;

    public JdOrderSyncTools(OrderService orderService,
                            WalletService walletService,
                            JdServer jdServer) {
        this.jdServer = jdServer;
        this.orderService = orderService;
        this.walletService = walletService;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @Async
    public void manualSyncOrder() {
        this.manualSyncOrder(LocalDateTime.of(2019, 3, 29, 14, 0), LocalDateTime.now());
    }

    /**
     * 手动同步订单
     *
     * @param startTime 开始时间
     * @param endTime   到结束时间
     */
    public void manualSyncOrder(LocalDateTime startTime, LocalDateTime endTime) {

        while (startTime.isBefore(endTime)) {
            this.logger.info("manual sync order startTime: {},async order progress,endTime  {}", startTime, endTime);

            this.asyncJDProgress(1, startTime, 1);

            this.asyncJDProgress(1, startTime, 2);

            startTime = startTime.plusMinutes(1);
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void asyncOrder() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(3);

        this.asyncJDProgress(1, startTime, 1);

        this.asyncJDProgress(1, startTime, 2);
    }

    /**
     * 淘宝订单同步器
     * <p>
     * 获取存在订单,若果存在更新,不存在创建.
     * <p>
     * 每次同步根据内容多少判断它的分页,500 数量.
     * <p>
     * 判断用户是否在同步频率里兑换过口令的商品ID.若果商品ID相同表示是该用户订单.
     * 兑换过口令成功,会将用户ID 放入redis KEY 为 sync:createPwd:{商品ID} 里.
     * <p>
     * 如果是结算订单同步将订单信息更新,改变用户余额.
     */
    private void asyncJDProgress(int page, LocalDateTime startTime, int queryType) {

        JsonNode jsonNode = this.jdServer.getOrders(page, queryType, startTime);
        Iterator<JsonNode> dataItr = jsonNode.findPath("data").elements();

        while (dataItr.hasNext()) {

            JsonNode node = dataItr.next();

            Order order = this.objectMapper.convertValue(node, Order.class);

            Order dbOrder = this.orderService.loadByTidAndType(order.getTradeId(), 2);

            if (ObjectUtil.isNotNull(dbOrder)) {
                order.setId(dbOrder.getId());
                order.setOpenId(dbOrder.getOpenId());
            }

            JsonNode skuList = node.findPath("skuList");

            skuList.elements().forEachRemaining(item -> {

                order.setNumIid(item.findPath("skuId").asLong());
                order.setItemTitle(item.findPath("skuName").asText());
                order.setItemNum(item.findPath("skuNum").asInt());
                order.setPrice(item.findPath("price").asDouble());
                order.setPayPrice(item.findPath("actualCosPrice").asDouble());
                order.setOrderType(item.findPath("unionTrafficGroup").asText());
                order.setAdzoneId(item.findPath("positionId").asText());
                order.setAdzoneName(item.findPath("pid").asText());

                //推广者获得的分成比率，对应联盟后台报表“分成比率”
                order.setCommissionRate(item.findPath("subSideRate").asDouble());
                //推广者获得的收入金额，对应联盟后台报表“预估收入”
                order.setCommission(item.findPath("estimateCosPrice").asDouble());
                // 效果预估，付款金额*(佣金比率+补贴比率)*分成比率
                order.setPubSharePreFee(item.findPath("estimateFee").asDouble());
                // 最终比例（分成比例+补贴比例）
                order.setIncomeRate(item.findPath("finalRate").asDouble());

                //	佣金金额
                order.setTotalCommissionFee(item.findPath("actualFee").asDouble());
                //	佣金比率
                order.setTotalCommissionRate(item.findPath("commissionRate").asDouble());

                // 结算订单同步
                if (queryType == 2 && order.getTkStatus() == 3) {
                    if (dbOrder.getTkStatus() != 3) {
                        this.walletService.plusBalance(order.getTradeId(), order.getOpenId(), order.getTotalCommissionFee());
                    }
                }
            });

            this.logger.info("同步京东订单,订单信息 {}", node);

            order.setAsyncTime(LocalDateTime.now());
            order.setType(2);
            order.setExtend(node.deepCopy());
            this.orderService.asyncSave(order);

            if (jsonNode.findPath("hasMore").asBoolean()) {
                this.asyncJDProgress(page + 1, startTime, queryType);
            }
        }
    }
}
