package com.alex.taobao.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * rebate-alliance OrderRepository
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByIdAndType(String tid, int type);

}
