package com.dangminhphuc.dev.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventCustomize3rd extends ApplicationEvent {
    private final String message;

    public EventCustomize3rd(Object source, String message) {
        super(source);
        this.message = message;
    }
}
