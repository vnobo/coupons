package com.alex.bootiful.security.model.repository;

import com.alex.bootiful.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * qinke-coupons com.alex.bootiful.security.model.repository.UserRepository
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/23
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
