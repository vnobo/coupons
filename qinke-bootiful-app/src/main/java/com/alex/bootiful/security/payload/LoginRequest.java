package com.alex.bootiful.security.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * qinke-coupons com.alex.bootiful.security.payload.LoginRequest
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
