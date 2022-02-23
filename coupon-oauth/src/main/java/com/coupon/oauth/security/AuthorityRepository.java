package com.coupon.oauth.security;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * com.alex.oauth.security.AuthorityRepository
 *
 * @author <a href="https://github.com/vnobo">Alex bob</a>
 * @date Created by 2021/4/27
 */
public interface AuthorityRepository extends R2dbcRepository<Authority, Integer> {

    /**
     * get by username
     *
     * @param username user id
     * @return user model
     */
    @Query("select * from se_authorities where username ilike :username")
    Flux<Authority> findByUsername(String username);
}