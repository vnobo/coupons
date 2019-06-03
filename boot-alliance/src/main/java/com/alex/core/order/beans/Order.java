package com.alex.core.order.beans;

import com.alex.ObjectNodeConverterJson;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * rebate-alliance Orders
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 微信用户id
     */
    @Column(name = "openid")
    private String openId;
    /**
     * 订单ID
     */
    private String tradeId;

    private String adzoneId;

    private String adzoneName;

    /**
     * 订单类型1,淘宝.2,京东.3,拼多多
     */
    private int type;
    /**
     * 商品ID
     */
    private long numIid;
    /**
     * 商品名称
     */
    private String itemTitle;
    /**
     * 商品数量
     */
    private int itemNum;
    /**
     * 商品单价
     */
    private double price;
    /**
     * 实际支付金额
     */
    private double payPrice;
    /**
     * 推广者获得的收入金额，对应联盟后台报表“预估收入'
     */
    private double commission;
    /**
     * 推广者获得的分成比率，对应联盟后台报表“分成比率
     */
    private double commissionRate;
    /**
     * 淘客订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 淘客订单结算时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime earningTime;
    /**
     * 淘客订单状态，3：订单结算，12：订单付款， 13：订单失效，14：订单成功
     * <p>
     * 京东状态:订单维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,
     * 6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.
     * 无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,
     * 13.无效-违规订单,14.无效-来源与备案网址不符,
     * 15.待付款,16.已付款,17.已完成,18.已结算）
     * 注：自2018/7/13起，自己推广自己下单已经允许返佣，故12无效码仅针对历史数据有效
     */
    private int tkStatus;
    /**
     * 订单类型，如天猫，淘宝
     */
    private String orderType;
    /**
     * 收入比率，卖家设置佣金比率+平台补贴比率
     */
    private double incomeRate;
    /**
     * 效果预估，付款金额*(佣金比率+补贴比率)*分成比率
     */
    private double pubSharePreFee;
    /**
     * 补贴比率
     */
    private double subsidyRate;
    /**
     * 付款金额
     */
    private double alipayTotalPrice;
    /**
     * 佣金结算比率
     */
    private double totalCommissionRate;

    /**
     * 佣金结算金额
     */
    private double totalCommissionFee;

    @LastModifiedDate
    private LocalDateTime asyncTime;

    @Convert(converter = ObjectNodeConverterJson.class)
    private ObjectNode extend;

    public static Order of(String openid, String tradeId, int type) {
        Order order = new Order();
        order.setOpenId(openid);
        order.setTradeId(tradeId);
        order.setType(type);
        return order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setExt1(String openId) {
        this.openId = openId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setTrade_id(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setOrderId(String tradeId) {
        this.tradeId = tradeId;
    }

    public long getNumIid() {
        return numIid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setNumIid(long numIid) {
        this.numIid = numIid;
    }

    public void setNum_iid(long numIid) {
        this.numIid = numIid;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItem_title(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public void setItem_num(int itemNum) {
        this.itemNum = itemNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public void setPay_price(double payPrice) {
        this.payPrice = payPrice;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public void setCommission_rate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreate_time(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setOrderTime(long createTime) {
        this.createTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(createTime), ZoneId.systemDefault());
    }

    public LocalDateTime getEarningTime() {
        return earningTime;
    }

    public void setEarningTime(LocalDateTime earningTime) {
        this.earningTime = earningTime;
    }

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setEarning_time(LocalDateTime earningTime) {
        this.earningTime = earningTime;
    }

    public void setFinishTime(long earningTime) {
        if (earningTime > 0) {
            this.earningTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(earningTime), ZoneId.systemDefault());
        }
    }

    public int getTkStatus() {
        return tkStatus;
    }

    public void setTkStatus(int tkStatus) {
        this.tkStatus = tkStatus;
    }

    public void setTk_status(int tkStatus) {
        this.tkStatus = tkStatus;
    }

    /**
     * 订单维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算）注：自2018/7/13起，自己推广自己下单已经允许返佣，故12无效码仅针对历史数据有效
     */
    public void setValidCode(int tkStatus) {

        if (18 == tkStatus) {
            this.tkStatus = 3;
        } else if (16 == tkStatus) {
            this.tkStatus = 12;
        } else if (17 == tkStatus) {
            this.tkStatus = 14;
        } else {
            this.tkStatus = tkStatus;
        }
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setOrder_type(String orderType) {
        this.orderType = orderType;
    }

    public double getIncomeRate() {
        return incomeRate;
    }

    public void setIncomeRate(double incomePate) {
        this.incomeRate = incomePate;
    }

    public void setIncome_rate(double incomePate) {
        this.incomeRate = incomePate;
    }

    public double getPubSharePreFee() {
        return pubSharePreFee;
    }

    public void setPubSharePreFee(double pubSharePreFee) {
        this.pubSharePreFee = pubSharePreFee;
    }

    public void setPub_share_pre_fee(double pubSharePreFee) {
        this.pubSharePreFee = pubSharePreFee;
    }

    public double getSubsidyRate() {
        return subsidyRate;
    }

    public void setSubsidyRate(double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public void setSubsidy_rate(double subsidyRate) {
        this.subsidyRate = subsidyRate;
    }

    public String getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(String adzoneId) {
        this.adzoneId = adzoneId;
    }

    public void setAdzone_id(String adzoneId) {
        this.adzoneId = adzoneId;
    }

    public String getAdzoneName() {
        return adzoneName;
    }

    public void setAdzoneName(String adzoneName) {
        this.adzoneName = adzoneName;
    }

    public void setAdzone_name(String adzoneName) {
        this.adzoneName = adzoneName;
    }

    public double getAlipayTotalPrice() {
        return alipayTotalPrice;
    }

    public void setAlipayTotalPrice(double alipayTotalPrice) {
        this.alipayTotalPrice = alipayTotalPrice;
    }

    public void setAlipay_total_price(double alipayTotalPrice) {
        this.alipayTotalPrice = alipayTotalPrice;
    }

    public double getTotalCommissionRate() {
        return totalCommissionRate;
    }

    public void setTotalCommissionRate(double totalCommissionRate) {
        this.totalCommissionRate = totalCommissionRate;
    }

    public void setTotal_commission_rate(double totalCommissionRate) {
        this.totalCommissionRate = totalCommissionRate;
    }

    public double getTotalCommissionFee() {
        return totalCommissionFee;
    }

    public void setTotalCommissionFee(double totalCommissionFee) {
        this.totalCommissionFee = totalCommissionFee;
    }

    public void setTotal_commission_fee(double totalCommissionFee) {
        this.totalCommissionFee = totalCommissionFee;
    }

    public LocalDateTime getAsyncTime() {
        return asyncTime;
    }

    public void setAsyncTime(LocalDateTime asyncTime) {
        this.asyncTime = asyncTime;
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
