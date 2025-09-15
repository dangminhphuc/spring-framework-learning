package com.dangminhphuc.dev.webmvc.dispatcher.hierarchy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangminhphuc.dev.webmvc.dispatcher.hierarchy.service.MyService;

@Configuration
public class RootConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
