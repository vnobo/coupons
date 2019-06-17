package com.alex.taobao;


import com.alex.taobao.TaoBaoRestException;

/**
 * rebate-alliance OrderArgumentsException
 * Created by 2019-02-26
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public class OrderArgumentsException extends TaoBaoRestException {

    public OrderArgumentsException(int code, String msg) {
        super(code, msg);
    }
}
