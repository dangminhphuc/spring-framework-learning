package com.dangminhphuc.dev.event.xml;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/// Published when the ApplicationContext is being closed by using the close() method on the ConfigurableApplicationContext interface or via a JVM shutdown hook. Here, "closed" means that all singleton beans will be destroyed. Once the context is closed, it reaches its end of life and cannot be refreshed or restarted.
public class ClosedListener implements ApplicationListener<ContextClosedEvent> {
    public ClosedListener() {
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // NOOP
    }
}
