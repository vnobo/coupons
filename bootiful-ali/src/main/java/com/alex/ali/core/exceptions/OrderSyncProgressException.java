package com.alex.ali.core.exceptions;

/**
 * com.alex.taobao.exceptions in  coupons
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/21
 */
public class OrderSyncProgressException extends AliRestException {
    public OrderSyncProgressException(int code, String msg) {
        super(code, msg);
    }
}
