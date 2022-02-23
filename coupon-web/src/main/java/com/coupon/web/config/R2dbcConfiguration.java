package com.coupon.web.config;

import com.coupon.web.converters.SetReadConverter;
import com.coupon.web.converters.SetWriteConverter;
import com.coupon.web.security.User;
import com.coupon.web.security.UserRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * com.alex.web.config.R2dbcConfiguration
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/28
 */
@Configuration
@EnableTransactionManagement
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:postgres://localhost:5432/coupons");
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new SetReadConverter());
        converterList.add(new SetWriteConverter());
        return new R2dbcCustomConversions(getStoreConversions(), converterList);
    }

    @Bean
    public CommandLineRunner start(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = User.builder().username("admin").password(passwordEncoder.encode("123456")).authorities(
                    Set.of("ROLE_ADMIN")).enabled(true).build();
            userRepository.save(user).subscribe();
        };
    }
}