package com.example.alexthbot.fab.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration

public class TelegramBotDataConfiguration {
    @Bean("doctors")
    public List<String> getDoctors() {
        return Arrays.asList("Врач травматолог", "Врач уролог", "Врач хирург", "Зубной техник", "Врач нарколог");
    }
}
