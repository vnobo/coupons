package com.alex.oauth.config;

import com.alex.oauth.security.Authority;
import com.alex.oauth.security.AuthorityRepository;
import com.alex.oauth.security.User;
import com.alex.oauth.security.UserRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Set;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * coupons in com.alex.web.config
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/14
 */
@Configuration
public class WebConfiguration{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Configuration(proxyBeanMethods = false)
    static class DatabaseInitializationConfiguration {

        @Autowired
        void initializeDatabase(ConnectionFactory connectionFactory) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource[] scripts = new Resource[] { resourceLoader.getResource("classpath:schema.sql"),
                    resourceLoader.getResource("classpath:data.sql") };
            new ResourceDatabasePopulator(scripts).populate(connectionFactory).block();
        }

    }
}