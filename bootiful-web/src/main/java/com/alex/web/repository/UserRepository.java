package com.alex.web.repository;

import com.alex.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * coupons in com.alex.web.model
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/14
 */
public interface UserRepository extends JpaRepository<User, String> {
}
