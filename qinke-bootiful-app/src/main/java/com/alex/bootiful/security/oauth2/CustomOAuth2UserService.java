package com.alex.bootiful.security.oauth2;

import cn.hutool.core.util.RandomUtil;
import com.alex.bootiful.security.model.AuthProvider;
import com.alex.bootiful.security.model.User;
import com.alex.bootiful.security.model.UserPrincipal;
import com.alex.bootiful.security.oauth2.user.OAuth2UserInfo;
import com.alex.bootiful.security.oauth2.user.OAuth2UserInfoFactory;
import com.alex.bootiful.security.model.repository.UserRepository;
import com.alex.bootiful.security.oexception.OAuth2AuthenticationProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * qinke-coupons com.alex.bootiful.security.oauth2.CustomOAuth2UserService
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/7
 */
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String TAO_BAO_REGISTRATION_ID = "taobao";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User;

        if (TAO_BAO_REGISTRATION_ID.equalsIgnoreCase(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
            Map<String, Object> userAttributes = Map.of("username", oAuth2UserRequest.getAdditionalParameters().get("taobao_user_nick"),
                    "taoBaoId", oAuth2UserRequest.getAdditionalParameters().get("taobao_user_id"),
                    "taoBaoName", oAuth2UserRequest.getAdditionalParameters().get("taobao_user_nick"));
            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            oAuth2User = new DefaultOAuth2User(authorities, userAttributes, "username");
        } else {
            oAuth2User = super.loadUser(oAuth2UserRequest);
        }

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Username not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByUsername(oAuth2UserInfo.getName());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setProvider(authProvider);
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(authProvider + "_" + oAuth2UserInfo.getName());
        user.setPassword(RandomUtil.randomString(8));

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }
}
