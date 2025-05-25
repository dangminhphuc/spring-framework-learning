package com.dangminhphuc.dev.event.annotation;


import com.dangminhphuc.dev.event.EventCustomize1st;
import com.dangminhphuc.dev.event.EventCustomize2nd;
import com.dangminhphuc.dev.event.EventCustomize3rd;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.*;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

public class EventsListener {

    public EventsListener() {
    }

    // Method executed when EventCustomize event published
    @Order(1)
    @EventListener({EventCustomize1st.class})
    public void eventCustomize1st(EventCustomize1st event) {
        // NOOP
    }

    // Return an ApplicationEvent that is auto publish
    @Order(2)
    @EventListener({EventCustomize2nd.class})
    public EventCustomize3rd eventCustomize2nd() {
        return new EventCustomize3rd(this, "EventCustomize2nd trigger EventCustomize3rd");
    }

    // Method executed when EventCustomize event published
    @Order(3)
    @EventListener({EventCustomize3rd.class})
    public void eventCustomize3rd() {
        // NOOP
    }

    @EventListener({ContextRefreshedEvent.class})
    public void contextRefreshedEvent() {
        // NOOP
    }

    @EventListener({ContextStartedEvent.class})
    public void contextStartedEvent() {
        // NOOP
    }

    @EventListener({ContextStoppedEvent.class})
    public void contextStopedEvent() {
        // NOOP
    }

    @EventListener({ContextClosedEvent.class})
    public void contextClosedEvent() {
        // NOOP
    }

    @EventListener({EventCustomize1st.class, ContextRefreshedEvent.class})
    public void onEventCustomizeOrRefreshed() {
        // NOOP
    }

    @EventListener({EventCustomize1st.class, ContextRefreshedEvent.class})
    public void onEventCustomizeOrRefreshed(ApplicationEvent event) {
        // NOOP
    }

    @EventListener(value = {EventCustomize1st.class, ContextRefreshedEvent.class, ContextClosedEvent.class},
            condition = "#event instanceof T(com.dangminhphuc.dev.event.EventCustomize1st)")
    public void onEventFiltered(ApplicationEvent event) {
        // NOOP
    }

    // Not Test
    @Async
    @EventListener({EventCustomize3rd.class})
    public void onEventCustomize3rdDelay5sAndExecuteAsync() throws InterruptedException {
        // NOOP
        Thread.sleep(5000);
    }

    // Not Test
    @EventListener({EventCustomize1st.class, EventCustomize2nd.class, EventCustomize3rd.class})
    public void onEventCalledInOrder() {
        // NOOP
    }

}
