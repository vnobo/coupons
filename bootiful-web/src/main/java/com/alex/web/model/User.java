package com.alex.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

/**
 * qinke-coupons com.alex.web.model.User
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
    private String username;
    @JsonIgnore
    private String password;
    private Boolean enabled;
    private Set<String> authorities;


}
