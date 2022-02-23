package com.coupon.oauth.config;

import com.coupon.oauth.converters.SetReadConverter;
import com.coupon.oauth.converters.SetWriteConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * com.alex.web.config.R2dbcConfiguration
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/28
 */
@Configuration
@EnableTransactionManagement
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    private final ObjectMapper objectMapper;

    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:postgres://localhost:5432/coupons");
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new SetReadConverter(this.objectMapper));
        converterList.add(new SetWriteConverter(this.objectMapper));
        return new R2dbcCustomConversions(getStoreConversions(), converterList);
    }

}