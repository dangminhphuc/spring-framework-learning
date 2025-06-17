package com.dangminhphuc.dev.event.xml;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Published when the ApplicationContext is initialized or refreshed (for example, by using the refresh() method on the ConfigurableApplicationContext interface). Here, &ldquo;initialized&rdquo; means that all beans are loaded, post-processor beans are detected and activated, singletons are pre-instantiated, and the ApplicationContext object is ready for use. As long as the context has not been closed, a refresh can be triggered multiple times, provided that the chosen ApplicationContext actually supports such &ldquo;hot&rdquo; refreshes. For example, XmlWebApplicationContext supports hot refreshes, but GenericApplicationContext does not.
 */
public class InitializedListener implements ApplicationListener<ContextRefreshedEvent> {
    public InitializedListener() {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // NOOP
    }
}
