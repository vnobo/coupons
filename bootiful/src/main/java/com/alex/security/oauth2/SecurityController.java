package com.alex.security.oauth2;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author billb
 */
@RestController
public class SecurityController {

    @GetMapping("userinfo")
    public Object index(HttpSession httpSession,
                        @AuthenticationPrincipal OAuth2User oauth2User) {
        return Map.of("user", oauth2User, "session", httpSession);
    }

}

