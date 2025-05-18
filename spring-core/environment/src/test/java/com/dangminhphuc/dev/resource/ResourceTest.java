package com.dangminhphuc.dev.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceTest {

    @BeforeEach
    public void beforeExecute() {
        System.clearProperty("spring.profiles.active");
    }

    @Test
    public void testDevResource() {
        System.setProperty("spring.profiles.active", "dev");
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        ConfigLoader configLoader = context.getBean("configLoader", ConfigLoader.class);
        assertEquals("dev@email.com", configLoader.getUsername());
        assertEquals("!@#$%^", configLoader.getPassword());
    }

    @Test
    public void testProdResource() {
        System.setProperty("spring.profiles.active", "prod");
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        ConfigLoader configLoader = context.getBean("configLoader", ConfigLoader.class);
        assertEquals("prod@email.com", configLoader.getUsername());
        assertEquals("@!((%))", configLoader.getPassword());
    }

    @Test
    public void testDefaultResource() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        ConfigLoader configLoader = context.getBean("configLoader", ConfigLoader.class);
        assertEquals("default", configLoader.getUsername());
        assertEquals("default", configLoader.getPassword());
    }
}
