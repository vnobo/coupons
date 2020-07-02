package com.alex.ali.core.exceptions;

import lombok.Data;
import org.springframework.expression.ExpressionException;

import java.io.Serializable;

/**
 * @author billb
 */
@Data
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
