package com.alex.wxmp.core.alisms;

import cn.hutool.json.JSONUtil;
import com.alex.wxmp.AbstractGenericService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alicloud.sms.ISmsService;
import org.springframework.util.StringUtils;

public abstract class AbstractSMSService extends AbstractGenericService {

    private ISmsService smsService;


    protected SendSmsResponse sender(SMS sms) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(StringUtils.collectionToDelimitedString(sms.getPhoneNumbers(), ","));
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(sms.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(sms.getTemplateCode());
        // 可选:模板中的变量替换JSON串,如模板内容为"【企业级分布式应用服务】,您的验证码为${code}"时,此处的值为
        request.setTemplateParam(JSONUtil.toJsonStr(sms.getTemplateParam()));

        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = this.smsService.sendSmsRequest(request);
        } catch (ClientException e) {
            this.logger.error("send sms error message is {}", e.getErrMsg());
            sendSmsResponse = new SendSmsResponse();
        }
        return sendSmsResponse;
    }

    @Autowired
    public void setSmsService(ISmsService smsService) {
        this.smsService = smsService;
    }
}
