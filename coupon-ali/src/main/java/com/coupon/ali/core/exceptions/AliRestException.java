package com.coupon.ali.core.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.ExpressionException;

import java.io.Serializable;

/**
 * @author billb
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AliRestException extends ExpressionException implements Serializable {

    private int code;
    private String msg;

    public AliRestException(String message) {
        super(message);
        this.msg = message;
    }

    public AliRestException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public AliRestException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public AliRestException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

}