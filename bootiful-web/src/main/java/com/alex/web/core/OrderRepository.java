package com.alex.web.core;

import com.alex.web.core.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * rebate-alliance OrderRepository
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Repository
public interface OrderRepository extends R2dbcRepository<Order, String> {

}
