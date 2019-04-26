package com.alex.bootiful.security.oauth2.user;

import java.util.Map;

/**
 * qinke-coupons com.alex.bootiful.security.oauth2.user.OAuth2UserInfo
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
