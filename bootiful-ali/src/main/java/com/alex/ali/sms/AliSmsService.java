package com.alex.ali.sms;

import cn.hutool.core.util.NumberUtil;
import com.alex.ali.config.AliProperties;
import com.alex.ali.mail.SMSService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author AlexBob
 */
@Service
@RequiredArgsConstructor
public class AliSmsService extends BaseSmsService implements SMSService {

    private final AliProperties wxMpProperties;

    @Override
    public void withdrawSuccess(String phone, double amount) {
        Map parmas = Map.of("mtname", wxMpProperties.getAppName(),
                "amount", NumberUtil.round(amount,2),
                "kefuphone", wxMpProperties.getEmail());
        SendSmsResponse response = this.sender(SmsOnly.of(List.of(phone),
                "NER智心开始",
                "SMS_161320302", parmas));
        if (response.getCode().equalsIgnoreCase("OK")) {
            this.logger.info("提现申请,转账短信已发送成功! 提现人 {}", phone);
        } else {
            this.logger.error("提现申请,转账短信发送失败,Message : {}", response.getMessage());
        }
    }
}
