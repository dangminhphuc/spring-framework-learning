package com.dangminhphuc.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }
}
