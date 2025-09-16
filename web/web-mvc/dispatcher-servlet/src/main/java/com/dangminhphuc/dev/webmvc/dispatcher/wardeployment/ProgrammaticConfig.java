package com.dangminhphuc.dev.webmvc.dispatcher.wardeployment;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.dangminhphuc.dev.webmvc.dispatcher.wardeployment")
public class ProgrammaticConfig {

}
