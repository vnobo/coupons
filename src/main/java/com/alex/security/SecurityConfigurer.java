package com.alex.security;

import com.alex.core.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                @Qualifier("dataSource") DataSource dataSource) throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(pbkdf2PasswordEncoder())
                .getUserDetailsService();
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManagerBean());
    }

    @Bean
    public Pbkdf2PasswordEncoder pbkdf2PasswordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/security/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                    .customUserType(User.class,"taobao");

    }

}
