package com.dangminhphuc.dev.event;

import com.dangminhphuc.dev.event.annotation.EventsListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class AnnotationConfigurationEventTest {
    private AnnotationConfigApplicationContext context;

    @BeforeEach
    public void before() {
        // create new
        this.context = new AnnotationConfigApplicationContext();
    }

    @Test
    @DisplayName("Should call customEvent() when EventCustomize is published")
    void shouldCallCustomEvent_whenEventCustomizeIsPublished() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());

        // register spy as a bean
        this.context.registerBean(EventsListener.class, () -> listener);

        this.context.refresh();

        // publish event
        this.context.publishEvent(new EventCustomize1st(this, "Hello Paul"));

        Mockito.verify(listener).eventCustomize1st(Mockito.any(EventCustomize1st.class));
    }

    @Test
    @DisplayName("")
    void shouldCallCustomEvent3rd_whenEventCustomize2ndIsPublished() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());

        // register spy as a bean
        this.context.registerBean(EventsListener.class, () -> listener);

        this.context.refresh();

        // publish event
        this.context.publishEvent(new EventCustomize2nd(this, "Hello Paul"));

        InOrder inOrder = Mockito.inOrder(listener);
        inOrder.verify(listener).eventCustomize2nd();
        inOrder.verify(listener).eventCustomize3rd();
    }

    @Test
    @DisplayName("Should call contextRefreshedEvent() when Spring context is refreshed")
    public void shouldCallContextRefreshedEvent_whenSpringContextIsRefreshed() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());

        // register spy as a bean
        context.registerBean(EventsListener.class, () -> listener);

        // context refresh will trigger ContextRefreshedEvent
        context.refresh();

        Mockito.verify(listener).contextRefreshedEvent();
    }

    @Test
    @DisplayName("Should call contextClosedEvent() when Spring context is closed")
    public void shouldCallContextClosedEvent_whenSpringContextIsClosed() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());

        // register spy as a bean
        context.registerBean(EventsListener.class, () -> listener);
        context.refresh();
        context.close();

        Mockito.verify(listener).contextClosedEvent();
    }

    @Test
    public void shouldListenSpecifiedEvents() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());

        // register spy as a bean
        context.registerBean(EventsListener.class, () -> listener);
        context.refresh();
        context.close();

        Mockito.verify(listener).onEventCustomizeOrRefreshed();
    }

    @Test
    public void shouldListenSpecifiedEvents_when() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());

        // register spy as a bean
        context.registerBean(EventsListener.class, () -> listener);

        context.refresh();
        context.close();

        Mockito.verify(listener).onEventCustomizeOrRefreshed(Mockito.any());
        Mockito.verify(listener).contextRefreshedEvent();
    }

    @Test
    public void shouldListenSpecifiedEvent_whenApplySpEL() {
        // create a spy
        EventsListener listener = Mockito.spy(new EventsListener());
        EventsListener customizeListener = Mockito.spy(new EventsListener());

        // register spy as a bean
        context.registerBean(EventsListener.class, () -> listener);
        context.registerBean(EventsListener.class, () -> customizeListener);
        context.refresh();

        // publish event
        context.publishEvent(new EventCustomize1st(this, "Hello Paul"));
        context.close();

        Mockito.verify(listener, Mockito.never()).onEventFiltered(Mockito.any(ApplicationEvent.class));
        Mockito.verify(customizeListener).eventCustomize1st(Mockito.any(EventCustomize1st.class));
    }

}
