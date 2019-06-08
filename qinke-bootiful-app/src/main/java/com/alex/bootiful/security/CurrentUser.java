package com.alex.bootiful.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * qinke-coupons com.alex.bootiful.security.CurrentUser
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/25
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
