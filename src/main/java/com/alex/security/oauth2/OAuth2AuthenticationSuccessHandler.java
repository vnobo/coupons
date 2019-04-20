package com.alex.security.oauth2;

        import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
        import org.springframework.stereotype.Component;

/**
 * qinke-coupons com.alex.security.oauth2.OAuth2AuthenticationSuccessHandler
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/12
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public OAuth2AuthenticationSuccessHandler(String defaultTargetUrl) {
        super(defaultTargetUrl);
    }
}
