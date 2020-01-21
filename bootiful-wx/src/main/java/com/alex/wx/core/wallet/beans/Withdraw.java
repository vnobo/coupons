package com.alex.wx.core.wallet.beans;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author AlexBob
 */
@Data
@NoArgsConstructor
public class Withdraw implements Serializable {


    private long id;
    private String openid;
    private String aliname;
    private String alipay;
    private double balance;
    private int status;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private JsonNode extend;

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

}
