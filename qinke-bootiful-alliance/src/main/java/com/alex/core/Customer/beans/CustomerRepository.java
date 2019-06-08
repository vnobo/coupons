package com.alex.core.Customer.beans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer>, QuerydslPredicateExecutor<Customer> {

    Optional<Customer> findByOpenId(String openid);

    Optional<Customer> findByPid_AdZoneId(String p3);
}
