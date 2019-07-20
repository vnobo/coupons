package com.alex.taobao;

import com.alex.taobao.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * rebate-alliance OrderSynchronizerTools
 * Created by 2019-02-27
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Log4j2
@Component
public class OrdersSyncScheduled {

    private final OrderService orderService;

    public OrdersSyncScheduled(OrderService orderService) {
        this.orderService = orderService;
    }

    @EventListener(ApplicationReadyEvent.class)
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
            this.orderService.asyncProgress(1, startTime,20, 2);
            this.orderService.asyncProgress(1, startTime, 20,3);
            startTime = startTime.plusMinutes(20);
        }
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void asyncOrder() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(4);
        this.orderService.asyncProgress(1, startTime,2, 2);
        this.orderService.asyncProgress(1, startTime, 2,3);

    }

}
