package com.dangminhphuc.dev.webmvc.dispatcher.config;

import com.dangminhphuc.dev.webmvc.dispatcher.EmbeddedJettyServer;
import com.dangminhphuc.dev.webmvc.dispatcher.ProgrammaticServletInitializerImpl;
import com.dangminhphuc.dev.webmvc.dispatcher.ServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.dangminhphuc.dev.webmvc.dispatcher"})
public class ProgrammaticConfig {

    public ServletInitializer servletInitializer() {
        return new ProgrammaticServletInitializerImpl();
    }

    @Bean
    public EmbeddedJettyServer embeddedJettyServer() {
        return new EmbeddedJettyServer(0, servletInitializer());
    }
}
