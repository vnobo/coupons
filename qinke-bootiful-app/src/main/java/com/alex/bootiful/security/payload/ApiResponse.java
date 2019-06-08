package com.alex.bootiful.security.payload;

import lombok.Data;

/**
 * qinke-coupons com.alex.bootiful.security.payload.ApiResponse
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
@Data
public class ApiResponse {

    private boolean success;
    private String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
