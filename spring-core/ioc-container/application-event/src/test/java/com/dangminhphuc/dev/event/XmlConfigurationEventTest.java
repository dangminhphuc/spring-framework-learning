package com.dangminhphuc.dev.event;

import com.dangminhphuc.dev.event.xml.ClosedListener;
import com.dangminhphuc.dev.event.xml.InitializedListener;
import com.dangminhphuc.dev.event.xml.XmlConfigurationCustomListener1;
import com.dangminhphuc.dev.event.xml.XmlConfigurationCustomListener2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class XmlConfigurationEventTest {
    @Test
    @DisplayName("")
    public void testCustomListener() {
        XmlConfigurationCustomListener1 listener = Mockito.spy(new XmlConfigurationCustomListener1());

        // create context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // add components to context
        context.addApplicationListener(listener);
        context.registerBean(EventCustomizePublisher.class);

        context.refresh();

        // Inject publisher (ApplicationContext tự inject ApplicationEventPublisher)
        EventCustomizePublisher publisher = context.getBean(EventCustomizePublisher.class);
        publisher.setPublisher(context);

        // Act
        publisher.sayHello();

        Mockito.verify(listener).onApplicationEvent(Mockito.any(EventCustomize1st.class));
    }

    @Test
    public void testMultiCustomListenersListenEvent() {
        XmlConfigurationCustomListener1 listener1 = Mockito.spy(new XmlConfigurationCustomListener1());
        XmlConfigurationCustomListener2 listener2 = Mockito.spy(new XmlConfigurationCustomListener2());

        // create context, add listener, register bean
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addApplicationListener(listener1);
        context.addApplicationListener(listener2);
        context.registerBean(EventCustomizePublisher.class);
        context.refresh();

        // Inject publisher (ApplicationContext tự inject ApplicationEventPublisher)
        EventCustomizePublisher publisher = context.getBean(EventCustomizePublisher.class);
        publisher.setPublisher(context);

        // Act
        publisher.sayHello();

        Mockito.verify(listener1).onApplicationEvent(Mockito.any(EventCustomize1st.class));
        Mockito.verify(listener2).onApplicationEvent(Mockito.any(EventCustomize1st.class));
    }

    @Test
    public void shouldCallDefaultMethodWhenContextIsInitializedListener() {
        InitializedListener listener = Mockito.spy(new InitializedListener());

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.addApplicationListener(listener);
        context.refresh();

        Mockito.verify(listener).onApplicationEvent(Mockito.any(ContextRefreshedEvent.class));
    }

    @Test
    public void shouldCallDefaultMethodWhenContextIsClose() {
        ClosedListener listener = Mockito.spy(new ClosedListener());

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.addApplicationListener(listener);
        context.refresh();

        context.close();
        Mockito.verify(listener).onApplicationEvent(Mockito.any(ContextClosedEvent.class));
    }

}