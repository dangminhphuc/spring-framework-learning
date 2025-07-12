package com.dangminhphuc.dev.validation;

import com.dangminhphuc.dev.Simple;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

class SimpleValidatorTest {
    private final SimpleValidator validator = new SimpleValidator();

    @Test
    void supports_shouldReturnTrueForPersonClass() {
        assertTrue(validator.supports(SimpleValidator.class));
    }


    @Test
    void validate_shouldNotHaveErrors_whenNameIsValid() {
        Simple s = new Simple("Fuck");
        Errors errors = new BeanPropertyBindingResult(s, "simpleObject");
        this.validator.validate(s, errors);

        assertFalse(errors.hasFieldErrors("name"));
        assertFalse(errors.hasErrors());
    }

    @Test
    void validate_shouldHaveNameErrors_whenNameIsEmpty() {
        Simple s = new Simple("");
        Errors errors = new BeanPropertyBindingResult(s, "simpleObject");
        this.validator.validate(s, errors);

        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("name"));
        assertEquals(errors.getFieldError("name").getCode(), "name.empty");
    }

    @Test
    void validate_shouldHaveNameErrors_whenNameIsInvalid() {
        Simple s = new Simple("dangminhphuc");
        Errors errors = new BeanPropertyBindingResult(s, "simpleObject");
        this.validator.validate(s, errors);

        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("name"));
        assertEquals(errors.getFieldError("name").getCode(), "dangminhphuc is admin");
    }
}