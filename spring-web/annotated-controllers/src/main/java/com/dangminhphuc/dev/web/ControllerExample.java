package com.dangminhphuc.dev.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ControllerExample {
    static class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class[]{RootConfig.class};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{WebConfig.class};
        }

        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }
    }

    @Configuration
    static class RootConfig {
    }

    @Configuration
    @EnableWebMvc
    @ComponentScan(basePackages = "com.dangminhphuc.dev.web")
    static class WebConfig {
    }

    @Controller
    @RequestMapping("/")
    static class ViewController {
        @GetMapping("/hello")
        public String hello() {
            return "";
        }
    }

    @RestController
    @RequestMapping("/apis")
    static class RESTController {
        @GetMapping("/{id}")
        public String get(@PathVariable String id) {
            return id;
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public String post(@RequestBody String id) {
            return """
                    {
                        "id": "%s"
                    }
                    """
                    .formatted(id);
        }
    }
}
