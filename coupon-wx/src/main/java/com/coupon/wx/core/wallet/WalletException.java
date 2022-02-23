package com.coupon.wx.core.wallet;


import com.coupon.wx.RestServerException;

public class WalletException extends RestServerException {

    public WalletException(String message) {
        super(message);
    }

    public WalletException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletException(int code, String msg) {
        super(code, msg);
    }

    public WalletException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

}