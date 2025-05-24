package com.dangminhphuc.dev.event;

import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

@Setter
public class HelloPublisher {
    private ApplicationEventPublisher publisher;

    public HelloPublisher() {
    }

    public void sayHello() {
        System.out.println("HelloPublisher");
        HelloEvent event = new HelloEvent(this, "Hello World");
        this.publisher.publishEvent(event);
    }
}
