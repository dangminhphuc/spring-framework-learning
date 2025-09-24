package com.dangminhphuc.dev.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LegacyConfig {
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/legacy/hello", legacyController());
        mapping.setUrlMap(urlMap);
        mapping.setOrder(0); // ưu tiên cao
        return mapping;
    }

    @Bean
    public org.springframework.web.servlet.mvc.Controller legacyController() {
        return (request, response) -> {
            response.getWriter().write("legacy/hello");
            return null; // viết thẳng vào response
        };
    }
}
