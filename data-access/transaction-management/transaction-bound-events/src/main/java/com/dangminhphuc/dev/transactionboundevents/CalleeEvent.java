package com.dangminhphuc.dev.transactionboundevents;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CalleeEvent extends ApplicationEvent {

    private final String name;

    public CalleeEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

}
