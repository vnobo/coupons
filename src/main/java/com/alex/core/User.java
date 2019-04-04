package com.alex.core;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Data
@Entity
public class User implements OAuth2User {

    @Id
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return "user";
    }
}
