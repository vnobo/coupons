package com.alex.wx.core;

public interface MailService {

    void withdrawNotice(long withdrawId, String username, String aliPay, double amount);
}
