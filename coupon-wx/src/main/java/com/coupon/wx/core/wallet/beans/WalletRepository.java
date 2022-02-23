package com.coupon.wx.core.wallet.beans;

import java.util.Optional;

public interface WalletRepository {
    Optional<Wallet> findByOpenid(String openId);
}