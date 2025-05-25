package com.dangminhphuc.dev.event;

import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

@Setter
public class EventCustomizePublisher {
    private ApplicationEventPublisher publisher;

    public EventCustomizePublisher() {
    }

    public void sayHello() {
        EventCustomize1st event = new EventCustomize1st(this, "Hello World");
        this.publisher.publishEvent(event);
    }
}
