package com.alex.ali.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * com.alex.ali.mail.MailServiceTest
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2020/1/21
 */
@SpringBootTest
class MailServiceTest {

    @Autowired
    MailService mailService;

    @org.junit.jupiter.api.Test
    void withdrawNotice() {
        this.mailService.withdrawNotice(1,"5199840@qq.com","5199840@qq.com",1);
    }
}
