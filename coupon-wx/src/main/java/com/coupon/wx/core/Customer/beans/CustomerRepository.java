package com.coupon.wx.core.Customer.beans;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findByOpenId(String openid);

    Optional<Customer> findByPid_AdZoneId(String p3);
}