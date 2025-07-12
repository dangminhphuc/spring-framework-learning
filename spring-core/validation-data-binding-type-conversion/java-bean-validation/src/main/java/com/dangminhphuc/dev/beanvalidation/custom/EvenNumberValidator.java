package com.dangminhphuc.dev.beanvalidation.custom;

public class EvenNumberValidator implements jakarta.validation.ConstraintValidator<EvenNumber, Integer> {

    @Override
    public boolean isValid(Integer value, jakarta.validation.ConstraintValidatorContext context) {
        // Check if the value is null or even
        return value == null || value % 2 == 0;
    }
}
