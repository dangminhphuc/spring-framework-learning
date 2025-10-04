package com.dangminhphuc.dev.web.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Locale;

public class LocaleExample {
    public static class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class[]{RootConfig.class};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{LocaleConfig.class};
        }

        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }
    }

    @Configuration
    @EnableWebMvc
    @ComponentScan("com.dangminhphuc.dev.web.locale")
    public static class RootConfig {
        // MessageSource, where Spring looks for messages_xx.properties files
        @Bean
        public MessageSource messageSource() {
            ResourceBundleMessageSource source = new ResourceBundleMessageSource();
            source.setBasename("messages");
            source.setDefaultEncoding("UTF-8");
            return source;
        }
    }

    @Configuration
    public static class LocaleConfig implements WebMvcConfigurer {
        @Bean
        public LocaleResolver localeResolver() {
            SessionLocaleResolver resolver = new SessionLocaleResolver();
            resolver.setDefaultLocale(Locale.ENGLISH);
            return resolver;
        }

        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
            interceptor.setParamName("lang");
            return interceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }
    }

    @Controller
    public static class WebController {

        @Autowired
        private MessageSource messageSource;

        @GetMapping("/welcome")
        @ResponseBody
        public String welcome(Locale locale) {
            return this.messageSource.getMessage("welcome.message", null, locale);
        }

    }
}