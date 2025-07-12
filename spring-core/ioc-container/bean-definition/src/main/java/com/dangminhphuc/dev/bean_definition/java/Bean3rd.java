package com.dangminhphuc.dev.bean_definition.java;

import org.springframework.stereotype.Component;

@Component
public class Bean3rd {
    private String message;

    public Bean3rd setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }
}
