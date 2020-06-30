package com.alex.ali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author billb
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AliApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliApplication.class, args);
    }
}
