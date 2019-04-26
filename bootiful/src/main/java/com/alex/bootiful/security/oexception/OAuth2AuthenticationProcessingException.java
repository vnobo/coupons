package com.alex.bootiful.security.oexception;

import org.springframework.security.core.AuthenticationException;

/**
 * qinke-coupons com.alex.bootiful.security.oexception.OAuth2AuthenticationProcessingException
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/23
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {

    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
