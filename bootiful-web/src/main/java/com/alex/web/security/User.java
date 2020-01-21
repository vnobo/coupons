package com.alex.web.security;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

/**
 * qinke-coupons com.alex.web.security.User
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/3
 */
@Data
@Table("users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;
    @With
    private String username;
    private String password;
    private Boolean enabled;
    private Set<String> authorities;


}
