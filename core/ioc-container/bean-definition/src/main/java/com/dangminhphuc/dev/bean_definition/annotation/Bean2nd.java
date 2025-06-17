package com.dangminhphuc.dev.bean_definition.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bean2nd {

    @Value("Hello Paul")
    private String message;

    public String getMessage() {
        return message;
    }
}
