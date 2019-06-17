package com.alex.wxmp.alliance.taobao;

import com.alex.wxmp.RestServerException;

public class TaobaoRestExcetion extends RestServerException {
    public TaobaoRestExcetion(int code, String msg) {
        super(code, msg);
    }
}
