package com.dangminhphuc.dev.validation;

import com.dangminhphuc.dev.Simple;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SimpleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Simple.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors e) {
        Simple p = (Simple) target;
        ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
        if (p.getName().equals("dangminhphuc")) {
            e.rejectValue("name", "dangminhphuc is admin");
        }
    }
}
