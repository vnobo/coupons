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

    public String getOpenId();

    public long getTradeId();

    public long getNumIid();

    public String getItemTitle();

    public int getItemNum();

    public double getPrice();

    public double getPayPrice();

    public double getCommission();

    public double getCommissionRate();

    public LocalDateTime getCreateTime();

    public LocalDateTime getEarningTime();

    public int getTkStatus();

    public String getOrderType();

    public double getIncomeRate();

    public double getPubSharePreFee();

    public double getSubsidyRate();

    public String getAdzoneId();

    public String getAdzoneName();

    public double getAlipayTotalPrice();

    public double getTotalCommissionRate();

    public double getTotalCommissionFee();

    public LocalDateTime getAsyncTime();

    public ObjectNode getExtend();
}
