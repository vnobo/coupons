package com.alex.web.repositories;

import com.alex.web.model.Order;
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
