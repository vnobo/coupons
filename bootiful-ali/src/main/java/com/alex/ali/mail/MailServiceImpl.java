package com.alex.ali.mail;

import com.alex.ali.config.AliProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author AlexBob
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl extends BaseMailService implements MailService {

    private final AliProperties properties;

    @Override
    public void withdrawNotice(long withdrawId, String username, String aliPay, double amount) {
        super.sender(MailOnly.of("测试账号....", username,
                username + "申请提现,请及时处理....",
                Map.of("username", username,
                        "aliName", username,
                        "aliPay", aliPay,
                        "amount", amount,
                        "time", LocalDateTime.now().toString(),
                        "url", "http://" + properties.getHost() + "/api/withdraw/transfer/" + withdrawId),
                "withdraw-notice.html"));
    }

}
