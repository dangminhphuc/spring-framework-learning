package com.dangminhphuc.dev.beanvalidation;

import com.dangminhphuc.dev.error.customize.Bar;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Custom Validation Error Message Test")
public class CustomizeErrorTest {

    private final Validator validator;

    private CustomizeErrorTest() {
        // Set up message source
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("validation_message");
        messageSource.setDefaultEncoding("UTF-8");

        // Spring-aware validator
        try (LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean()) {
            factory.setValidationMessageSource(messageSource);
            factory.afterPropertiesSet();

            this.validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Should return custom error message for invalid Bar.name")
    void shouldReturnCustomErrorMessage_whenBeanIsInvalid() {
        Bar bar = new Bar(); // Invalid: name is empty
        bar.setNumber(0);
        bar.setString("");

        Set<ConstraintViolation<Bar>> violations = this.validator.validate(bar);
        List<String> errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        List<String> expectedMessages = List.of(
                "The value must be greater than or equal to 1",
                "The value must not be null"
        );
        assertEquals(expectedMessages, errorMessages);
    }
}