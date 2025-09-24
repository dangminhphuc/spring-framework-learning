package com.dangminhphuc.dev.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.dangminhphuc.dev")
@Import({ViewResolverConfig.class, LegacyConfig.class})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Add static resource handlers for CSS, JS, images, etc.
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("/static/")
//                .setCachePeriod(3600);
//
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("/css/")
//                .setCachePeriod(3600);
//
//        registry.addResourceHandler("/js/**")
//                .addResourceLocations("/js/")
//                .setCachePeriod(3600);
    }
}
