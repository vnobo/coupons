package com.alex.oauth.security;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ObjectUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * com.alex.oauth.security.Authorities
 *
 * @author <a href="https://github.com/vnobo">Alex bob</a>
 * @date Created by 2021/4/25
 */
@Data
@Entity
@Table(name = "se_authorities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements Serializable, Persistable<Integer> {

    @Id
    private Integer id;
    private String username;
    private String authority;
    private Set<String> permissions;

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