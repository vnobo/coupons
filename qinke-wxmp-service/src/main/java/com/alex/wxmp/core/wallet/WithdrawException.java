package com.alex.wxmp.core.wallet;

public class WithdrawException extends WalletException {
    public WithdrawException(int code, String msg) {
        super(code, msg);
    }
}
