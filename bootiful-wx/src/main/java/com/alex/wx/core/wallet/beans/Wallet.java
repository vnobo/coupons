package com.alex.wx.core.wallet.beans;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Wallet implements Serializable {

    private int id;
    private String openid;
    private String promoter;
    private String alipay;
    private String aliname;
    private double balance;
    private double totalBalance;
    /**
     * 用户返利比率
     * 不能大于70%
     * 不能小于50%
     */
    @DecimalMax(value = "1")
    @DecimalMin(value = "0.05")
    private double rate;

    // 推广佣金
    private double brokerage;

    /**
     * 用户佣金返利比率
     * 不能大于10%
     * 不能小于5%
     */
    @DecimalMax(value = "1")
    @DecimalMin(value = "0.05")
    private double brokerageRate;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private ObjectNode extend;

    public Wallet(String openid, double balance,
                  @DecimalMax(value = "0.7") @DecimalMin(value = "0.5") double rate,
                  double brokerage, @DecimalMax(value = "0.1") @DecimalMin(value = "0.05")
                          double brokerageRate, ObjectNode extend) {
        this.openid = openid;
        this.balance = balance;
        this.rate = rate;
        this.brokerage = brokerage;
        this.brokerageRate = brokerageRate;
        this.extend = extend;
    }

    public static Wallet of(String openid,
                            @DecimalMax(value = "0.7") @DecimalMin(value = "0.5") double rate,
                            @DecimalMax(value = "0.1") @DecimalMin(value = "0.05") double brokerageRate) {
        return of(openid, 0, rate, 0, brokerageRate, null);
    }

    public static Wallet of(String openid,
                            double balance,
                            @DecimalMax(value = "0.7") @DecimalMin(value = "0.5") double rate,
                            double brokerage,
                            @DecimalMax(value = "0.1") @DecimalMin(value = "0.05") double brokerageRate,
                            ObjectNode extend) {
        return new Wallet(openid, balance, rate, brokerage, brokerageRate, extend);
    }


}
