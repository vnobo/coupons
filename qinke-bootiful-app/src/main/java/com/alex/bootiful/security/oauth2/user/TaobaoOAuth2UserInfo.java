package com.alex.bootiful.security.oauth2.user;

import java.util.Map;

/**
 * qinke-coupons com.alex.bootiful.security.oauth2.user.TaobaoOAuth2UserInfo
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
public class TaobaoOAuth2UserInfo extends OAuth2UserInfo {

    public TaobaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("taoBaoId");
    }

    @Override
    public String getName() {
        return (String) attributes.get("taoBaoName");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
