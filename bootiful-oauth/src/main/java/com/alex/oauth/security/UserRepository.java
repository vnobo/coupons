package com.alex.oauth.security;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * coupons in com.alex.web.model
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/14
 */
public interface UserRepository extends R2dbcRepository<User, Integer> {

    /**
     * get by username
     *
     * @param username user id
     * @return user model
     */
    @Query("select * from se_users where username ilike :username")
    Mono<User> findByUsername(String username);

    /**
     * get by username
     *
     * @param username user id
     * @return user model
     */
    @Modifying
    @Query("update se_users set last_login_time=current_timestamp where username ilike :username")
    Mono<Integer> setLastLoginTime(String username);
}