package com.alex.wx.wechat;

import com.alex.wx.RestServerException;

public class WxRestException extends RestServerException {
    public WxRestException(int code, String msg) {
        super(code, msg);
    }
}
