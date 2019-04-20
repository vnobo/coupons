package com.alex.security.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * qinke-coupons com.alex.security.oauth2.CustomOAuth2UserService
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/7
 */
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String TAO_BAO_REGISTRATION_ID = "taobao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        if (!TAO_BAO_REGISTRATION_ID.equalsIgnoreCase(userRequest.getClientRegistration().getRegistrationId())) {
            return super.loadUser(userRequest);
        }

        Assert.notNull(userRequest, "userRequest cannot be null");

        Map<String, Object> userAttributes = Map.of("username", userRequest.getAdditionalParameters().get("taobao_user_nick"),
                "taoBaoId", userRequest.getAdditionalParameters().get("taobao_user_id"),
                "taoBaoName", userRequest.getAdditionalParameters().get("taobao_user_nick"));

        Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));

        return new DefaultOAuth2User(authorities, userAttributes, "username");
    }
}
