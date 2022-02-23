package com.coupon.ali.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * boot-cool-alliance TbKeConfiguration
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Configuration
@EnableScheduling
@EnableConfigurationProperties({AliProperties.class})
public class AliConfiguration implements WebFluxConfigurer {


}