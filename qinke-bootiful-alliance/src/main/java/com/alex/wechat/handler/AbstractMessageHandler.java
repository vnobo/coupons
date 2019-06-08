package com.alex.wechat.handler;

import cn.hutool.core.util.ObjectUtil;
import com.alex.config.RateType;
import com.alex.core.Customer.CustomerService;
import com.alex.core.Customer.beans.Customer;
import com.alex.core.wallet.WalletService;
import com.alex.core.wallet.beans.Wallet;
import com.alex.wechat.config.WxMpProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * boot-cool-alliance AbstractHandler
 * Created by 2019-02-14
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Component
public abstract class AbstractMessageHandler implements WxMpMessageHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected ObjectMapper objectMapper;

    protected CustomerService customerService;

    protected WalletService walletService;

    protected WxMpProperties wxMpProperties;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) throws WxErrorException {

        this.logger.info("wxMessage is: " + wxMessage.getContent() + " user id: " + wxMessage.getFromUser());

        // 用户不存在,初始化用户
        if (!this.customerService.exists(wxMessage.getFromUser())) {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMessage.getFromUser());
            logger.debug("initialize customer  open id is " + wxMessage.getFromUser());
            if (ObjectUtil.isNotNull(wxMpUser)) {
                Customer customer = Customer.of(wxMpUser.getOpenId());
                BeanUtils.copyProperties(wxMpUser, customer);
                customer.getExtend().putPOJO("info", wxMpUser);
                customer.setAsyncTime(LocalDateTime.now());
                this.customerService.save(customer);
            }
        }

        // 用户钱包不存在,初始化用户的钱包
        if (!this.walletService.exists(wxMessage.getFromUser())) {
            logger.debug("initialize wallet  open id is " + wxMessage.getFromUser());
            this.walletService.save(Wallet.of(wxMessage.getFromUser(), RateType.USER_COMMISSION_RATE.value(), RateType.BROKERAGE_COMMISSION_RATE.value()));
        }

        return customizeHandle(wxMessage, context, wxMpService, sessionManager);
    }

    protected WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                                WxMpService wxMpService,
                                                WxSessionManager sessionManager) throws WxErrorException {

        return WxMpXmlOutMessage.TEXT().content(wxMessage.getContent())
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setWalletService(WalletService walletService) {
        this.walletService = walletService;
    }

    @Autowired
    public void setWxMpProperties(WxMpProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }
}
