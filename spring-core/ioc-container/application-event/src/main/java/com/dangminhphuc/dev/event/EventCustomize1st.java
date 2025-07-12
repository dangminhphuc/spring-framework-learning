package com.dangminhphuc.dev.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventCustomize1st extends ApplicationEvent {
    private final String message;

    public EventCustomize1st(Object source, String message) {
        super(source);
        this.message = message;
    }
}
