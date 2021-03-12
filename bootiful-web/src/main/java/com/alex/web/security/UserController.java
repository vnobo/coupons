package com.alex.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * com.alex.web.security.UserController
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/28
 */
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/search/name/{username}")
    public Flux findUserByName(@PathVariable String username) {
        return Flux.interval(Duration.ofSeconds(10))
                        .flatMap(tick->this.userRepository.findByUsername(username))
                .map(user -> {
                    var only = new UserOnly();
                    BeanUtils.copyProperties(user,only);
                    return only;
                });
    }
}
