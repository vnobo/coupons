package com.alex.bootiful.security.oauth2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * qinke-coupons com.alex.bootiful.security.oauth2.UserService
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/5/23
 */
@Service
public class UserService {

    @Transactional(propagation = Propagation.SUPPORTS)
    public void methodA() {

    }
}
