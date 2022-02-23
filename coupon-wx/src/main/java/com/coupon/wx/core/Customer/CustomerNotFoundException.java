package com.coupon.wx.core.Customer;

import com.coupon.wx.RestServerException;

/**
 * CustomerNotFoundException
 */
public class CustomerNotFoundException extends RestServerException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerNotFoundException(int code, String msg) {
        super(code, msg);
    }

    public CustomerNotFoundException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}