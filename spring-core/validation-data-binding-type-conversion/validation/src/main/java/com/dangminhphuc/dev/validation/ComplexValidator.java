package com.dangminhphuc.dev.validation;

import com.dangminhphuc.dev.Complex;
import com.dangminhphuc.dev.Simple;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ComplexValidator implements Validator {
    // chill validator
    private final Validator simpleValidator;

    public ComplexValidator(Validator simpleValidator) {
        if (simpleValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is required and must not be null.");
        }
        if (!simpleValidator.supports(Simple.class)) {
            throw new IllegalArgumentException("The supplied [Validator] must support the validation of [Simple] instances.");
        }
        this.simpleValidator = simpleValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Complex.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors e) {
        Complex c = (Complex) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "code", "code.empty");
        try {
            e.pushNestedPath("simple");
            ValidationUtils.invokeValidator(this.simpleValidator, c.getSimple(), e);
        } catch (RuntimeException ex) {
            e.popNestedPath();
        }
    }
}
