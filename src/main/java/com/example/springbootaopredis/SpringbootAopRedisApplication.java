package com.example.springbootaopredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.example.springbootaopredis"})
public class SpringbootAopRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAopRedisApplication.class, args);
    }

}
