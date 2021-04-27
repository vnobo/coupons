package com.alex.oauth.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * com.alex.oauth.security.SecurityService
 *
 * @author <a href="https://github.com/vnobo">Alex bob</a>
 * @date Created by 2021/4/25
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .flatMap(user -> this.getAuthorities(username)
                        .map(authoritiesArray -> this.buildUserDetails(user, authoritiesArray)))
                .doOnSuccess(userDetails -> this.setLastLoginTime(userDetails.getUsername())
                        .subscribe(res -> log.info("用户{}登录成功!", username)));
    }

    private UserDetails buildUserDetails(User user, String... authorities) {
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .authorities(authorities)
                .password(user.getPassword())
                .disabled(!user.getEnabled())
                .accountExpired(!user.getEnabled())
                .credentialsExpired(!user.getEnabled())
                .accountLocked(!user.getEnabled())
                .build();
    }

    private Mono<String[]> getAuthorities(String username) {
        return this.authorityRepository.findByUsername(username)
                .map(Authority::getAuthority)
                .collectList()
                .map(a -> a.toArray(new String[0]));
    }

    public Mono<Integer> setLastLoginTime(String username) {
        return this.userRepository.setLastLoginTime(username);
    }
}