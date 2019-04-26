package com.alex.bootiful.security.oauth2.user;

import java.util.Map;

/**
 * qinke-coupons com.alex.bootiful.security.oauth2.user.GithubOAuth2UserInfo
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
public class GithubOAuth2UserInfo extends OAuth2UserInfo {
    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
