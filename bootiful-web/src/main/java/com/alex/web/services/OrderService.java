package com.alex.web.services;

import com.alex.web.repository.OrderRepository;
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
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "order")
    public void processMessage(String content) {
        log.info("tao bao order listener {}",content);
    }
}
