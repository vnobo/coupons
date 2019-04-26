package com.alex.bootiful.security.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * qinke-coupons com.alex.bootiful.security.payload.SignUpRequest
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
@Data
public class SignUpRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
