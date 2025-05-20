package com.dangminhphuc.dev.resource.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:${spring.profiles.active}.properties")
@ComponentScan(basePackages = "com.dangminhphuc.dev.resource")
public class AppConfig {
}
