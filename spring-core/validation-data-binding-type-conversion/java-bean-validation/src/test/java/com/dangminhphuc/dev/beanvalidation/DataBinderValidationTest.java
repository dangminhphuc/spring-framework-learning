package com.dangminhphuc.dev.beanvalidation;

import com.dangminhphuc.dev.databinder.Bar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Map;

public class DataBinderValidationTest {
    @Test
    void test() {
        Bar bar = new Bar();
        DataBinder binder = new DataBinder(bar);
        binder.setValidator(new LocalValidatorFactoryBean());

        PropertyValues pvs = new MutablePropertyValues(Map.of(
                "number", 0
        ));
        binder.bind(pvs);
        binder.validate();

        binder.getBindingResult().getAllErrors().forEach(error -> {
            System.out.println("Error: " + error.getDefaultMessage());
        });
    }
}
