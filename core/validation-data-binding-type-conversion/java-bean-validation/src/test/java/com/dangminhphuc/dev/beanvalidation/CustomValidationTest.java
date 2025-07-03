package com.dangminhphuc.dev.beanvalidation;

import com.dangminhphuc.dev.beanvalidation.custom.Foo;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void customConstraint_whenNumberIsEven_then() {
        // Create an instance of Foo with an even number
        Foo foo = new Foo();
        foo.setNumber(2);

        // Validate the instance
        var violations = this.validator.validate(foo);

        // Assert that there are no violations
        assertTrue(violations.isEmpty(), "Expected no validation errors, but found: " + violations);

    }

    @Test
    void customConstraint_whenNumberIsOdd_then() {
        // Create an instance of Foo with an even number
        Foo foo = new Foo();
        foo.setNumber(1);

        // Validate the instance
        var violations = this.validator.validate(foo);

        // Assert that there is a violation
        assertFalse(violations.isEmpty(), "Expected one validation error, but found: " + violations.size());

    }
}
