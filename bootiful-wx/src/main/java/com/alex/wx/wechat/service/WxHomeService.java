package com.alex.wx.wechat.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alex.wx.BaseGenericService;
import com.alex.wx.core.Customer.CustomerService;
import com.alex.wx.core.Customer.beans.Customer;
import com.alex.wx.core.wallet.WalletException;
import com.alex.wx.core.wallet.WalletService;
import com.alex.wx.core.wallet.beans.Wallet;
import com.alex.wx.wechat.WxRestException;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * rebate-alliance WxHomeService
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Service
public class WxHomeService extends BaseGenericService {

    private CustomerService customerService;
    private WxMpService wxMpService;
    private WalletService walletService;

    public WxHomeService(CustomerService customerService,
                         WxMpService wxMpService,
                         WalletService walletService) {
        this.customerService = customerService;
        this.wxMpService = wxMpService;
        this.walletService = walletService;
    }


    public WxJsapiSignature wxJsapi(String url) {
        try {
            return this.wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            throw new WxRestException(500, e.getMessage());
        }
    }



    public Wallet getWallet(String openId) {
        Wallet wallet = this.walletService.loadByOpenId(openId);
        if (ObjectUtil.isNull(wallet)) {
            throw new WalletException(500, openId + " 用户钱包不存在.");
        }
        return wallet;
    }

    /**
     * 提取现金
     *
     * @param openId 参数
     */
    public Wallet withdraw(String openId) {

        if (ObjectUtil.isNull(openId)) {
            throw new WalletException(500, "openId 参数不能为空.");
        }

        return this.walletService.withdraw(openId);
    }

   /* *//**
     * 绑定订单
     *
     * @param params 参数
     *//*
    public void commitOrder(Map<String, String> params) {

        String tradeId = params.getOrDefault("tradeId", null);
        String openId = params.getOrDefault("openId", null);
        String type = params.getOrDefault("type", null);

        if (ObjectUtil.isNull(openId)) throw new OrderArgumentsException(501, "open id is required");
        if (ObjectUtil.isNull(tradeId)) throw new OrderArgumentsException(502, "trade id is required");
        if (ObjectUtil.isNull(type)) throw new OrderArgumentsException(503, "type is required");

        Order order = this.orderService.loadByTidAndType(tradeId, Integer.valueOf(type));

        if (ObjectUtil.isNotNull(order)) {
            if (StringUtils.isBlank(order.getOpenId()) || order.getTotalCommissionFee() == 0) {
                order.setOpenId(openId);
            } else {
                throw new OrderArgumentsException(500, "this model openId is build user id " + order.getOpenId());
            }
            this.orderService.asyncSave(order);
        } else {
            throw new OrderArgumentsException(501, "this model is null,wait 2 minute build");
        }

    }
*/

    /**
     * 绑定支付宝
     *
     * @param params 参数
     *//*
    public void bindingAli(Map<String, String> params) {

        String alipay = params.getOrDefault("alipay", null);
        String aliname = params.getOrDefault("aliname", null);
        String openId = params.getOrDefault("openId", null);

        if (ObjectUtil.isNull(alipay)) throw new OrderArgumentsException(501, "alipayis required");
        if (ObjectUtil.isNull(aliname)) throw new OrderArgumentsException(503, "aliname is required");
        if (ObjectUtil.isNull(openId)) throw new OrderArgumentsException(502, "trade id is required");

        Wallet wallet = this.walletService.loadByOpenId(openId);
        if (ObjectUtil.isNotNull(wallet)) {
            if (StringUtils.isBlank(wallet.getAlipay())) {
                wallet.setAlipay(alipay);
                wallet.setAliname(aliname);
            } else {
                throw new OrderArgumentsException(500, "你已经绑定了支付宝," + wallet.getAlipay());
            }
            this.walletService.asyncSave(wallet);
        } else {
            throw new OrderArgumentsException(501, "this wallet is null,pl check build");
        }

    }
*/

    /**
     * 第一次登陆页面
     *
     * @param code 换取用户ID
     */
    public Map loadByCode(String code) throws WxErrorException {

        WxOAuth2AccessToken wxMpOAuth2AccessToken = this.wxMpService.getOAuth2Service().getAccessToken(code);

        this.logger.debug("customer is {} ", JSONUtil.toJsonStr(wxMpOAuth2AccessToken));

        WxMpUser wxMpUser = this.wxMpService.getUserService().userInfo(wxMpOAuth2AccessToken.getOpenId(),
                "zh_CN");

        // 更新用户信息
        Customer customer = this.customerService.loadByOpenId(wxMpUser.getOpenId());

        if (ObjectUtil.isNull(customer)) {
            customer = new Customer();
        }

        this.syncCustomer(wxMpUser, customer);

        return this.loadByOpenId(customer.getOpenId());

    }

    /**
     * 根据用户ID 获取用户整合信息
     */
    public Map loadByOpenId(String openId) {

        Assert.notEmpty(openId, "openid is required.");

        Customer customer = this.customerService.loadByOpenId(openId);
        Wallet wallet = this.walletService.loadByOpenId(openId);
        long shareNumber = this.walletService.getSumShardUser(openId);

        return Map.of("info", customer, "shareNumber", shareNumber, "wallet", wallet);

    }

    private void syncCustomer(WxMpUser wxMpUser, Customer customer) {
        BeanUtils.copyProperties(wxMpUser, customer);
        customer.getExtend().putPOJO("info", wxMpUser);
        customer.setAsyncTime(LocalDateTime.now());
    }

}
