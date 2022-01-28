package com.jjyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jjyu.mapper")
public class TimeTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimeTaskApplication.class, args);
    }

}
