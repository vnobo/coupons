package com.alex.ali.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * boot-cool-alliance TbKeConfiguration
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Configuration
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties(TaoBaoProperties.class)
public class TaoBaoFluxConfiguration {


}
