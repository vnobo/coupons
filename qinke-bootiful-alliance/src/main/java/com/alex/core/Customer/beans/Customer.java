package com.alex.core.Customer.beans;

import cn.hutool.core.util.ObjectUtil;
import com.alex.ObjectNodeConverterJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wx_customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "openid")
    private String openId;

    @OneToOne
    @JoinColumn(name = "id")
    private TBPid pid;

    private String nickname;

    private int sex;

    private String city;

    private String country;

    private String province;

    private String headImgUrl;

    @Convert(converter = ObjectNodeConverterJson.class)
    private ObjectNode extend;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @CreatedDate
    private LocalDateTime asyncTime;


    public static Customer of(int id) {
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    public static Customer of(String openid) {
        Customer customer = new Customer();
        customer.setOpenId(openid);
        return customer;
    }


    /**
     * 构建微信公众号,用户基本信息
     *
     * @param id       微信ID
     * @param nickname 昵称
     * @param sex      性别
     * @param city     城市
     * @param country  国家
     * @param province 省份
     * @param info     用户信息主体
     */
    public static Customer of(int id, String openid, String nickname,
                              int sex, String city, String country,
                              String province, ObjectNode info) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setOpenId(openid);
        customer.setNickname(nickname);
        customer.setSex(sex);
        customer.setCity(city);
        customer.setCountry(country);
        customer.setProvince(province);
        customer.getExtend().putPOJO("info", info);
        return customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public TBPid getPid() {
        return pid;
    }

    public void setPid(TBPid pid) {
        this.pid = pid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
    
    public ObjectNode getExtend() {
        if (ObjectUtil.isNull(this.extend)) {
            this.extend = new ObjectMapper().createObjectNode();
        }
        return extend;
    }

    public void setExtend(ObjectNode extend) {
        this.extend = extend;
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

    public LocalDateTime getAsyncTime() {
        return asyncTime;
    }

    public void setAsyncTime(LocalDateTime asyncTime) {
        this.asyncTime = asyncTime;
    }
}
