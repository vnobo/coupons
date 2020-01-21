package com.alex.ali.sms;

import cn.hutool.json.JSONUtil;
import com.alex.ali.BaseGenericService;
import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Log4j2
public abstract class BaseSmsService extends BaseGenericService {

    private ISmsService smsService;

    protected SendSmsResponse sender(SmsOnly smsOnly) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(StringUtils.collectionToDelimitedString(smsOnly.getPhoneNumbers(), ","));
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(smsOnly.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsOnly.getTemplateCode());
        // 可选:模板中的变量替换JSON串,如模板内容为"【企业级分布式应用服务】,您的验证码为${code}"时,此处的值为
        request.setTemplateParam(JSONUtil.toJsonStr(smsOnly.getTemplateParam()));

        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = this.smsService.sendSmsRequest(request);
        } catch (ClientException e) {
           log.error("send sms error message is {}", e.getErrMsg());
            sendSmsResponse = new SendSmsResponse();
        }
        return sendSmsResponse;
    }

    @Autowired
    public void setSmsService(ISmsService smsService) {
        this.smsService = smsService;
    }
}
