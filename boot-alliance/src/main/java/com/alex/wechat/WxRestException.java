package com.alex.wechat;

import com.alex.RestServerException;

public class WxRestException extends RestServerException {
    public WxRestException(int code, String msg) {
        super(code, msg);
    }
}
