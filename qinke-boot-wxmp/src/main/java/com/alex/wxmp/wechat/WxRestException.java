package com.alex.wxmp.wechat;

import com.alex.wxmp.RestServerException;

public class WxRestException extends RestServerException {
    public WxRestException(int code, String msg) {
        super(code, msg);
    }
}
