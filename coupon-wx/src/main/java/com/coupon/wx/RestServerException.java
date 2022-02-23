package com.coupon.wx;

import org.springframework.expression.ExpressionException;

import java.io.Serializable;

public abstract class RestServerException extends ExpressionException implements Serializable {

    private int code;
    private String msg;

    public RestServerException(String message) {
        super(message);
        this.msg = message;
    }

    public RestServerException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public RestServerException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public RestServerException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}