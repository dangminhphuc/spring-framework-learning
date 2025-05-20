package com.dangminhphuc.dev.resource;

import com.dangminhphuc.dev.resource.annotation.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceAnnotationConfigurationTest {

    @BeforeEach
    public void beforeExecute() {
        System.clearProperty("spring.profiles.active");
    }


    @Test
    public void testDevResource() {
        System.setProperty("spring.profiles.active", "dev");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConfigLoader configLoader = context.getBean(ConfigLoader.class);
        assertEquals("dev@email.com", configLoader.getUsername());
        assertEquals("!@#$%^", configLoader.getPassword());
        assertEquals("Development", configLoader.getEnvironmentName());
    }

    @Test
    public void testProdResource() {
        System.setProperty("spring.profiles.active", "prod");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ConfigLoader configLoader = context.getBean(ConfigLoader.class);
        assertEquals("prod@email.com", configLoader.getUsername());
        assertEquals("@!((%))", configLoader.getPassword());
        assertEquals("Production", configLoader.getEnvironmentName());
    }
}
