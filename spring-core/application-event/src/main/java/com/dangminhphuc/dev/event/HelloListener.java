package com.dangminhphuc.dev.event;

import org.springframework.context.ApplicationListener;

public class HelloListener implements ApplicationListener<HelloEvent> {

    public HelloListener() {
    }

    @Override
    public void onApplicationEvent(HelloEvent event) {
        System.out.println("HelloListener: " + event.getMessage());
        // Application logic here
    }
}
