package com.alex.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * qinke-coupons com.alex.web.model.User
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/3
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String password;

    private Boolean enable;

}
