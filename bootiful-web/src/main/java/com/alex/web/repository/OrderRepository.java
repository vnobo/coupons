package com.alex.web.repository;

import com.alex.web.model.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * rebate-alliance OrderRepository
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public interface OrderRepository extends R2dbcRepository<Order, String> {

}
