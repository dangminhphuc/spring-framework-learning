package com.dangminhphuc.dev.beanvalidation;

import com.dangminhphuc.dev.beanvalidation.jakarta.Bar;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

public class BeanValidationTest {

    @Nested
    class JakartaValidationTest {

        private jakarta.validation.Validator validator;

        @BeforeEach
        void setUp() {
            ValidatorFactory factory = buildDefaultValidatorFactory();
            this.validator = factory.getValidator();
        }

        @Test
        @DisplayName("Test Jakarta Bean Validation with valid input")
        void jakartaValidation_whenInputInvalidValue_thenReturnErrors() {
            Bar bar = new Bar();
            bar.setMin(-1);
            bar.setMax(101);
            bar.setString("");
            bar.setSize(List.of("a", "b", "c", "d"));
            bar.setEmail("invalid-email");
            bar.setPattern("invalid_pattern!");
            bar.setFoo(null);

            Map<String, String> errors = Map.of("min", "Min value must not be less than 0", "max", "Max value must not exceed 100", "string", "String must not be blank", "size", "Size must be between 1 and 3", "email", "Email must be valid", "pattern", "Pattern must match alphanumeric characters only", "foo", "must not be null");

            Set<ConstraintViolation<Bar>> violations = this.validator.validate(bar);

            assertFalse(violations.isEmpty());
            violations.forEach(violation -> {
                boolean isContain = errors.containsKey(violation.getPropertyPath().toString());
                assertTrue(isContain);

                String expectedMessage = errors.get(violation.getPropertyPath().toString());
                assertEquals(expectedMessage, violation.getMessage(), "Expected: " + expectedMessage + ", but got: " + violation.getMessage());

            });

        }
    }

    @Nested
    class SpringValidationTest {
        private org.springframework.validation.Validator validator;
        // see validation module for more details
    }
}
