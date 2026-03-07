package com.configandauth.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AuthandconfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthandconfigApplication.class, args);
    }
}