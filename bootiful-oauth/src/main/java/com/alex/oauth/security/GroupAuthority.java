package com.alex.oauth.security;

import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ObjectUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * com.alex.oauth.security.GroupAuthorities
 *
 * @author <a href="https://github.com/vnobo">Alex bob</a>
 * @date Created by 2021/4/25
 */
@Data
@Entity
@Table(name = "se_group_authorities")
public class GroupAuthority implements Serializable, Persistable<Integer> {
    @Id
    private Integer id;

    @Version
    private Long version;

    @Override
    public boolean isNew() {
        return ObjectUtils.isEmpty(this.id);
    }
}