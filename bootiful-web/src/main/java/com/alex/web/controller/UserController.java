package com.alex.web.controller;

import com.alex.web.model.User;
import com.alex.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * com.alex.web.controller.UserController
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/28
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/search/name/{username}")
    public Mono<User> findUserByName(@PathVariable String username){

    }
}
