package com.alex.taobao.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.expression.ExpressionException;

import java.io.Serializable;

/**
 * @author billb
 */
@Data
@EqualsAndHashCode(callSuper = false)
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
}
