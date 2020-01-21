package com.alex.ali.mail;

import com.alex.ali.config.AliProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author AlexBob
 */
@Service
public class DefaultMailServiceImpl extends BaseMailService implements MailService {

    private AliProperties wxMpProperties;

    public DefaultMailServiceImpl(AliProperties wxMpProperties) {
        this.wxMpProperties = wxMpProperties;
    }

    @Override
    public void withdrawNotice(long withdrawId, String username, String aliPay, double amount) {

        this.sender(MailOnly.of(wxMpProperties.getAppName(), wxMpProperties.getEmail(),
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
