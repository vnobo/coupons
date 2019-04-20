package com.alex.security.oauth2;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author billb
 */
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    private String username;
    private String taoBaoId;
    private String taoBaoName;
}
