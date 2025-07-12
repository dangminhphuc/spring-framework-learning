package com.dangminhphuc.dev.beanvalidation;

import com.dangminhphuc.dev.methodvalidation.Bar;
import com.dangminhphuc.dev.methodvalidation.ValidationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.validation.method.ParameterValidationResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MethodValidationTest {

    private ApplicationContext context;
    private Bar bar;

    @BeforeEach
    void setUp() {
        this.context = new AnnotationConfigApplicationContext(ValidationConfig.class);
        this.bar = context.getBean(Bar.class);
    }

    @Test
    void methodValidation_whenInputValid_then() {
        assertEquals("Hello", bar.doSomething("Hello"));
    }

    @Test
    void methodValidation_whenInputInvalid_thenThrowMethodValidationException() {
        assertThrows(MethodValidationException.class, () -> bar.doSomething(null));
    }


    @Test
    void methodValidation_whenInputInvalid_thenThrowMethodValidationExceptionAdvance() {
        MethodValidationException ex = assertThrows(MethodValidationException.class, () -> bar.doSomething(null));
        List<ParameterValidationResult> valueResults = ex.getValueResults();
        valueResults.forEach(result -> {
        });
    }

}
