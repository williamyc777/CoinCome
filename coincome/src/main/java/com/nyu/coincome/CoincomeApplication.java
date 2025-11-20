package com.nyu.coincome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.nyu.coincome.mapper")
public class CoincomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoincomeApplication.class, args);
    }
}

