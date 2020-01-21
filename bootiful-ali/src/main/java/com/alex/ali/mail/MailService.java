package com.alex.ali.mail;

public interface MailService {

    void withdrawNotice(long withdrawId, String username, String aliPay, double amount);
}
