package com.alex.bootiful.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author billb
 */
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    private String email;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    private String imageUrl;

    /**
     * 渠道关系ID是
     * 这个渠道的唯一标识(举例合作方邀请了小方成为他的渠道，
     * 小方会有一个唯一的关系id12345)，
     * 后续为小方提供推广商品时，推广链接中要带上小方的关系id
     * ，转链推广及数据效果跟踪均依赖此ID，务必保证正确。
     */
    private String relationId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

}
