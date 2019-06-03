package com.alex.alliance.taobao;

import com.alex.RestServerException;

public class TaobaoRestExcetion extends RestServerException {
    public TaobaoRestExcetion(int code, String msg) {
        super(code, msg);
    }
}
