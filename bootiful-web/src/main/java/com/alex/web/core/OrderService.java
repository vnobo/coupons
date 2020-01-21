package com.alex.web.core;

import com.alex.web.core.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * com.alex.web.services in  coupons
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/21
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    @RabbitListener(queues = "order")
    public void processMessage(String content) {
        log.info("tao bao order listener {}",content);
    }
}
