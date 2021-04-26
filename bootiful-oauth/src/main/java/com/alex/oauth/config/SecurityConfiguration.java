package com.alex.oauth.config;

import com.alex.oauth.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * coupons in com.alex.web.config
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/14
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserRepository userRepository;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges ->
                exchanges.anyExchange().authenticated())
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()));
        return http.build();
    }

}