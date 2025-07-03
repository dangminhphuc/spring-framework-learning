package com.dangminhphuc.dev.error.customize;

import com.dangminhphuc.dev.methodvalidation.Bar;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ValidationConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("validation_message");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public com.dangminhphuc.dev.methodvalidation.Bar bar() {
        return new Bar();
    }
}
