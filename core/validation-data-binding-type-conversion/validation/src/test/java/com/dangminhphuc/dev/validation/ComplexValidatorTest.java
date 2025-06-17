package com.dangminhphuc.dev.validation;

import com.dangminhphuc.dev.Complex;
import com.dangminhphuc.dev.Simple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexValidatorTest {
    private final ComplexValidator validator = new ComplexValidator(new SimpleValidator());

    private Complex complex;

    @BeforeEach
    void setUp() {
        this.complex = new Complex();
    }

    @Test
    void supports_shouldReturnTrueForComplexClass() {
        assertTrue(validator.supports(Complex.class));
    }

    @Test
    void validate_shouldHaveErrors_whenNotInputComplexName() {
        Errors errors = new BeanPropertyBindingResult(this.complex, "complexObject");
        this.validator.validate(this.complex, errors);

        assertTrue(errors.hasFieldErrors("name"));
        assertEquals("code.empty", errors.getFieldError("code").getCode());
    }

    @Test
    void validate_shouldHaveErrors_whenSimpleValidatorHaveErrors() {
        Simple invalidSimple = new Simple("dangminhphuc");
        this.complex.setCode("001");
        this.complex.setSimple(invalidSimple);

        Errors errors = new BeanPropertyBindingResult(this.complex, "complexObject");
        this.validator.validate(this.complex, errors);

        assertTrue(errors.hasFieldErrors("name"));
        assertEquals("dangminhphuc is admin", errors.getFieldError("name").getCode());
    }

}