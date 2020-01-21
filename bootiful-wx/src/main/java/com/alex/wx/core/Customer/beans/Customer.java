package com.alex.wx.core.Customer.beans;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Customer {

    private int id;
    private String openId;
    private TBPid pid;
    private String nickname;
    private int sex;
    private String city;
    private String country;
    private String province;
    private String headImgUrl;
    private ObjectNode extend;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private LocalDateTime asyncTime;

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

}
