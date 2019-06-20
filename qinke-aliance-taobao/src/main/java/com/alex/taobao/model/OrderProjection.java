package com.alex.taobao.model;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;

/**
 * rebate-alliance OrderProjection
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public interface OrderProjection {

    public String getId();

    String getOpenId();

    long getTradeId();

    long getNumIid();

    String getItemTitle();

    int getItemNum();

    double getPrice();

    double getPayPrice();

    double getCommission();

    double getCommissionRate();

    LocalDateTime getCreateTime();

    LocalDateTime getEarningTime();

    int getTkStatus();

    String getOrderType();

    double getIncomeRate();

    double getPubSharePreFee();

    double getSubsidyRate();

    String getAdzoneId();

    String getAdzoneName();

    double getAlipayTotalPrice();

    double getTotalCommissionRate();

    double getTotalCommissionFee();

    LocalDateTime getAsyncTime();

    ObjectNode getExtend();
}
