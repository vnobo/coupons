package com.alex.web.model;

import lombok.Data;

/**
 * qinke-coupons com.alex.web.model.User
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/3
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private Boolean enable;

}
