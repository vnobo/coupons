package com.alex.taobao.exceptions;

/**
 * com.alex.taobao.exceptions in  coupons
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/21
 */
public class OrderSyncProgressException extends TaoBaoRestException {
    public OrderSyncProgressException(int code, String msg) {
        super(code, msg);
    }
}
