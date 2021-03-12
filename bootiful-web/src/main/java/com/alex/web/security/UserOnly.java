package com.alex.web.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * com.alex.web.security.UserOnly
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2020/1/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOnly {
    private Integer id;
    private String username;
    private Boolean enabled;
    private Set<String> authorities;
}

