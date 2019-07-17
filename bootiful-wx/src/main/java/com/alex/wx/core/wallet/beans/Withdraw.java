package com.alex.wx.core.wallet.beans;

import com.alex.wx.ObjectNodeConverterJson;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdraws")
@EntityListeners(AuditingEntityListener.class)
public class Withdraw implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String openid;
    private String aliname;
    private String alipay;
    private double balance;
    private int status;
    @CreatedDate
    private LocalDateTime created_Time;
    @LastModifiedDate
    private LocalDateTime update_time;
    @Convert(converter = ObjectNodeConverterJson.class)
    private JsonNode extend;


    public Withdraw() {
    }

    public Withdraw(String openid, String aliname, String alipay, double balance) {
        this.openid = openid;
        this.aliname = aliname;
        this.alipay = alipay;
        this.balance = balance;
    }

    /**
     * 构建提现日志
     * @param openid 用户
     * @param aliname 真实姓名
     * @param alipay 支付宝
     * @param balance 提现额度
     * @return
     */
    public static Withdraw of(String openid, String aliname, String alipay, double balance) {
        return new Withdraw(openid,aliname,alipay,balance);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAliname() {
        return aliname;
    }

    public void setAliname(String aliname) {
        this.aliname = aliname;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreated_Time() {
        return created_Time;
    }

    public void setCreated_Time(LocalDateTime created_Time) {
        this.created_Time = created_Time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public JsonNode getExtend() {
        return extend;
    }

    public void setExtend(JsonNode extend) {
        this.extend = extend;
    }
}
