package com.alex.bootiful.security.payload;

import lombok.Data;

/**
 * qinke-coupons com.alex.bootiful.security.payload.AuthResponse
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
