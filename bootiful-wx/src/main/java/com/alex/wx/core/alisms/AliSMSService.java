package com.alex.wx.core.alisms;

import cn.hutool.core.util.NumberUtil;
import com.alex.wx.core.SMSService;
import com.alex.wx.wechat.config.WxMpProperties;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AliSMSService extends AbstractSMSService implements SMSService {

    private WxMpProperties wxMpProperties;

    public AliSMSService(WxMpProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }

    @Async
    public void withdrawSuccess(String phone, double amount) {
        Map parmas = Map.of("mtname", wxMpProperties.getAppName(),
                "amount", NumberUtil.round(amount,2),
                "kefuphone", wxMpProperties.getEmail());
        SendSmsResponse response = this.sender(SMS.of(List.of(phone),
                "NER智心开始",
                "SMS_161320302", parmas));
        if (response.getCode().equalsIgnoreCase("OK")) {
            this.logger.info("提现申请,转账短信已发送成功! 提现人 {}", phone);
        } else {
            this.logger.error("提现申请,转账短信发送失败,Message : {}", response.getMessage());
        }
    }
}
