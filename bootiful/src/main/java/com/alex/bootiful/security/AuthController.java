package com.alex.bootiful.security;

import com.alex.bootiful.security.model.AuthProvider;
import com.alex.bootiful.security.model.User;
import com.alex.bootiful.security.model.UserPrincipal;
import com.alex.bootiful.security.oexception.BadRequestException;
import com.alex.bootiful.security.oexception.ResourceNotFoundException;
import com.alex.bootiful.security.payload.ApiResponse;
import com.alex.bootiful.security.payload.AuthResponse;
import com.alex.bootiful.security.payload.LoginRequest;
import com.alex.bootiful.security.payload.SignUpRequest;
import com.alex.bootiful.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

/**
 * qinke-coupons com.alex.bootiful.security.AuthController
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/27
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public User getCurrentUser(Principal userPrincipal) {
        return userRepository.findByUsername(userPrincipal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getName()));
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              HttpSession httpSession) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new AuthResponse(httpSession.getId()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getName())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setUsername(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }
}
