package com.dangminhphuc.dev.methodvalidation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ValidationConfig {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setProxyTargetClass(true);
        postProcessor.setAdaptConstraintViolations(true); // Bật MethodValidationException

        return postProcessor;
    }

    @Bean
    public Bar bar() {
        return new Bar();
    }
}
