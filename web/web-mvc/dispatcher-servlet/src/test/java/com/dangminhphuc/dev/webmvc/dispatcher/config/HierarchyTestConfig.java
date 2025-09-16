package com.dangminhphuc.dev.webmvc.dispatcher.config;

import com.dangminhphuc.dev.webmvc.dispatcher.EmbeddedJettyServer;
import com.dangminhphuc.dev.webmvc.dispatcher.ServletInitializer;
import com.dangminhphuc.dev.webmvc.dispatcher.wardeployment.WebAppInitializer;
import jakarta.servlet.ServletException;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HierarchyTestConfig {

    @Bean
    public ServletInitializer hierarchyServletInitializer() {
        return () -> {
            try {
                ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
                handler.setContextPath("/");

                WebAppInitializer initializer = new WebAppInitializer();
                initializer.onStartup(handler.getServletContext());

                return handler;
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public EmbeddedJettyServer embeddedJettyServer() {
        return new EmbeddedJettyServer(0, hierarchyServletInitializer());
    }
}
