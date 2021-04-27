package com.alex.oauth.security;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.ObjectUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * qinke-coupons com.alex.web.security.User
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/3
 */
@Data
@Entity
@Table("se_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, Persistable<Integer> {

    @Id
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Version
    private Long version;

    @Override
    public boolean isNew() {
        return ObjectUtils.isEmpty(this.id);
    }
}