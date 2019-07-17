package com.alex.wx.core.wallet.beans;

import com.alex.wx.ObjectNodeConverterJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@EntityListeners(AuditingEntityListener.class)
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String openid;

    @Column(name = "promoter")
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

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @Convert(converter = ObjectNodeConverterJson.class)
    private ObjectNode extend;

    public Wallet() {
    }

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPromoter() {
        return promoter;
    }

    public void setPromoter(String promoter) {
        this.promoter = promoter;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getAliname() {
        return aliname;
    }

    public void setAliname(String aliname) {
        this.aliname = aliname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(double brokerage) {
        this.brokerage = brokerage;
    }

    public double getBrokerageRate() {
        return brokerageRate;
    }

    public void setBrokerageRate(double brokerageRate) {
        this.brokerageRate = brokerageRate;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ObjectNode getExtend() {
        if (ObjectUtils.isEmpty(this.extend)) {
            this.extend = new ObjectMapper().createObjectNode();
        }
        return extend;
    }

    public void setExtend(ObjectNode extend) {
        this.extend = extend;
    }
}
