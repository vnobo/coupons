package com.alex.ali.core.exceptions;

/**
 * rebate-alliance OrderArgumentsException
 * Created by 2019-02-26
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public class OrderArgumentsException extends AliRestException {

    public OrderArgumentsException(int code, String msg) {
        super(code, msg);
    }
}
