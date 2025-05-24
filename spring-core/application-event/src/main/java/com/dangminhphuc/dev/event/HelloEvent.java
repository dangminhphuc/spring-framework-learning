package com.dangminhphuc.dev.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class HelloEvent extends ApplicationEvent {
    private final String message;

    public HelloEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
