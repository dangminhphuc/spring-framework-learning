package com.dangminhphuc.dev.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventCustomize2nd extends ApplicationEvent {
    private final String message;

    public EventCustomize2nd(Object source, String message) {
        super(source);
        this.message = message;
    }
}
