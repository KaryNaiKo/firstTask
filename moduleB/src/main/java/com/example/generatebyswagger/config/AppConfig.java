package com.example.generatebyswagger.config;

import com.example.generatebyswagger.scheduler.ScheduledTasks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public ScheduledTasks bean() {
        return new ScheduledTasks();
    }
}