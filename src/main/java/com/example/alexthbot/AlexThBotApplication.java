package com.example.alexthbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AlexThBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlexThBotApplication.class, args);
    }

}
