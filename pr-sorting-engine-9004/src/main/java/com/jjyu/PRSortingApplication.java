package com.jjyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jjyu.mapper")
public class PRSortingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PRSortingApplication.class, args);
    }
}
