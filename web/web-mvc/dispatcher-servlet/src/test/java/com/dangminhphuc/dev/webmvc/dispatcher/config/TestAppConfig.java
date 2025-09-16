package com.dangminhphuc.dev.webmvc.dispatcher.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.dangminhphuc.dev.webmvc.dispatcher.controller")
public class TestAppConfig {
}
