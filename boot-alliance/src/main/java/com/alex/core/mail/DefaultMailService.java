package com.alex.core.mail;

import com.alex.core.MailService;
import com.alex.wechat.config.WxMpProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class DefaultMailService extends AbstractMailService implements MailService {

    private WxMpProperties wxMpProperties;

    public DefaultMailService(WxMpProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }

    @Async
    public void withdrawNotice(long withdrawId, String username, String aliPay, double amount) {

        this.sender(Mail.of(wxMpProperties.getAppName(), wxMpProperties.getEmail(),
                username + "申请提现,请及时处理....",
                Map.of("username", username,
                        "aliName", username,
                        "aliPay", aliPay,
                        "amount", amount,
                        "time", LocalDateTime.now().toString(),
                        "url", "http://" + wxMpProperties.getHost() + "/api/withdraw/transfer/" + withdrawId),
                "withdraw-notice.html"));
    }

}
