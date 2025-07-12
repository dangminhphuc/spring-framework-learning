package com.dangminhphuc.dev.databinding;

import com.dangminhphuc.dev.ConstructorBindingObject;
import org.springframework.core.ResolvableType;
import org.springframework.validation.DataBinder;

import java.util.Map;

public class ConstructorBinder {
    private final Map<String, ?> input;

    public ConstructorBinder(Map<String, ?> input) {
        this.input = input;
    }

    public ConstructorBindingObject construct() throws Exception {
        DataBinder binder = new DataBinder(null);
        binder.setTargetType(ResolvableType.forClass(ConstructorBindingObject.class));
        binder.construct(new SimpleValueResolver(this.input));
        if (binder.getBindingResult().hasErrors()) {
            throw new Exception("Binding error");
        }
        return (ConstructorBindingObject) binder.getTarget();
    }
}
