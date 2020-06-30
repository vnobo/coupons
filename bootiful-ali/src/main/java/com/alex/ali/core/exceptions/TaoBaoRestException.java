package com.alex.ali.core.exceptions;

import lombok.EqualsAndHashCode;
import org.springframework.expression.ExpressionException;

import java.io.Serializable;

/**
 * @author billb
 */
@EqualsAndHashCode
public class TaoBaoRestException extends ExpressionException implements Serializable {

    private int code;
    private String msg;

    public TaoBaoRestException(String message) {
        super(message);
        this.msg = message;
    }

    public TaoBaoRestException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public TaoBaoRestException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public TaoBaoRestException(int code, String message, Throwable cause) {
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
