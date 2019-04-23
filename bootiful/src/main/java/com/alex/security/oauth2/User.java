package com.alex.security.oauth2;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
