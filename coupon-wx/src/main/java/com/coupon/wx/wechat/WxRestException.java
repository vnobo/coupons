package com.coupon.wx.wechat;

import com.coupon.wx.RestServerException;

public class WxRestException extends RestServerException {
    public WxRestException(int code, String msg) {
        super(code, msg);
    }
}