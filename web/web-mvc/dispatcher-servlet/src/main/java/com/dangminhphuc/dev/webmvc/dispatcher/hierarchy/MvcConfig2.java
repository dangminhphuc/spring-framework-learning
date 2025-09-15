package com.dangminhphuc.dev.webmvc.dispatcher.hierarchy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.dangminhphuc.dev.webmvc.dispatcher.hierarchy.controller2")
public class MvcConfig2 {

}
