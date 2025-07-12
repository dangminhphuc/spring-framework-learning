package com.dangminhphuc.dev.bean_definition.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaBasedConfiguration {

    @Bean
    public Bean3rd bean3rd() {
        return new Bean3rd().setMessage("Hello Paul");
    }
}
