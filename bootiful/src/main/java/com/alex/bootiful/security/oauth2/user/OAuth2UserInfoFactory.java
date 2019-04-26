package com.alex.bootiful.security.oauth2.user;

import com.alex.bootiful.security.model.AuthProvider;
import com.alex.bootiful.security.oexception.OAuth2AuthenticationProcessingException;

import java.util.Map;

/**
 * qinke-coupons com.alex.bootiful.security.oauth2.user.OAuth2UserInfoFactory
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.taobao.toString())) {
            return new TaobaoOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
