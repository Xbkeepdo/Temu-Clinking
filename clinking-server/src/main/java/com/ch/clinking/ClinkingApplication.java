package com.ch.clinking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.ch.clinking.mapper")
public class ClinkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinkingApplication.class, args);
    }

}
