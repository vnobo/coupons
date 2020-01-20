package com.alex.web.config;

import com.alex.web.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * coupons in com.alex.web.config
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/14
 */
@Order(10)
@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomServerAuthenticationEntryPoint())
                .and()
                .authorizeExchange()
                .anyExchange().authenticated();
        return http.build();
    }

    @Bean(autowireCandidate = false)
    public AuthenticationWebFilter authenticationWebFilter() {
        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(reactiveAuthenticationManager());
        authenticationFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(new CustomServerAuthenticationEntryPoint()));
        authenticationFilter.setServerAuthenticationConverter(new ServerHttpBasicAuthenticationConverter());
        authenticationFilter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        return authenticationFilter;
    }

    @Bean(autowireCandidate = false)
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new CustomAuthenticationManager(userDetailsService(), this.passwordEncoder());
    }

    @Bean(autowireCandidate = false)
    public ReactiveUserDetailsService userDetailsService() {
        return (username) -> this.userRepository.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername())
                        .password(u.getPassword())
                        .authorities(u.getAuthorities().toArray(new String[0]))
                        .accountExpired(!u.getEnabled())
                        .credentialsExpired(!u.getEnabled())
                        .disabled(!u.getEnabled())
                        .accountLocked(!u.getEnabled())
                        .build());
    }


    static class CustomAuthenticationManager extends UserDetailsRepositoryReactiveAuthenticationManager {

        CustomAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                    PasswordEncoder passwordEncoder) {
            super(userDetailsService);
            this.setPasswordEncoder(passwordEncoder);
        }

        @Override
        public Mono<Authentication> authenticate(Authentication authentication) {
            return super.authenticate(authentication);
        }
    }

    static class CustomServerAuthenticationEntryPoint extends HttpBasicServerAuthenticationEntryPoint {
        @Override
        public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
            List<String> requestedWith = exchange.getRequest().getHeaders().get("X-Requested-With");
            if (requestedWith != null && requestedWith.contains("XMLHttpRequest")) {
                return Mono.fromRunnable(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                });
            }
            return super.commence(exchange, e);
        }
    }

}
