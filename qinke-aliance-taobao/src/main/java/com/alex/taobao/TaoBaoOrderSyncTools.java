package com.alex.wxmp.alliance.taobao;

import com.alex.wxmp.AbstractGenericService;
import com.alex.wxmp.core.order.OrderService;
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
@Component
public class TaoBaoOrderSyncTools extends AbstractGenericService {


    private OrderService orderService;


    public TaoBaoOrderSyncTools(OrderService orderService) {
        this.orderService = orderService;
    }

    //@EventListener(ApplicationReadyEvent.class)
    @Async
    public void manualSyncOrder() {
        this.manualSyncOrder(LocalDateTime.of(2019, 3, 29, 0, 0), LocalDateTime.now());
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

            this.orderService.asyncProgress(1, startTime, "create_time", 20 * 60);

            this.orderService.asyncProgress(1, startTime, "settle_time", 20 * 60);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startTime = startTime.plusMinutes(20);
        }
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void asyncOrder() {

        LocalDateTime startTime = LocalDateTime.now().minusMinutes(3);

        this.orderService.asyncProgress(1, startTime, "create_time", 60 * 2);

        this.orderService.asyncProgress(1, startTime, "settle_time", 60 * 2);

    }

}
