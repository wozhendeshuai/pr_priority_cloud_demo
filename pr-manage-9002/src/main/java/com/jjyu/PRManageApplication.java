package com.jjyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PRManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PRManageApplication.class, args);
    }
}

