package com.jjyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jjyu.mapper")
public class PRManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PRManageApplication.class, args);
    }
}

