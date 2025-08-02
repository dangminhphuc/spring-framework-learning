package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@DisplayName("Spring Integration Tests")
public class SpringIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MyService myService;

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private LoggingAspect loggingAspect;

    @Test
    @DisplayName("Should load Spring application context")
    public void testContextLoads() {
        assertNotNull(applicationContext);
    }

    @Test
    @DisplayName("Should autowire MyService bean")
    public void testMyServiceBeanAutowired() {
        assertNotNull(myService);
        assertTrue(myService instanceof MyService);
    }

    @Test
    @DisplayName("Should autowire LoggerService bean")
    public void testLoggerServiceBeanAutowired() {
        assertNotNull(loggerService);
        assertTrue(loggerService instanceof LoggerService);
        assertTrue(loggerService instanceof ConsoleLoggerService);
    }

    @Test
    @DisplayName("Should autowire LoggingAspect bean")
    public void testLoggingAspectBeanAutowired() {
        assertNotNull(loggingAspect);
        assertTrue(loggingAspect instanceof LoggingAspect);
    }

    @Test
    @DisplayName("Should have MyService as Spring-managed bean")
    public void testMyServiceIsSpringSingleton() {
        MyService anotherInstance = applicationContext.getBean(MyService.class);
        assertSame(myService, anotherInstance);
    }

    @Test
    @DisplayName("Should have LoggerService as Spring-managed bean")
    public void testLoggerServiceIsSpringSingleton() {
        LoggerService anotherInstance = applicationContext.getBean(LoggerService.class);
        assertSame(loggerService, anotherInstance);
    }

    @Test
    @DisplayName("Should have LoggingAspect as Spring-managed bean")
    public void testLoggingAspectIsSpringSingleton() {
        LoggingAspect anotherInstance = applicationContext.getBean(LoggingAspect.class);
        assertSame(loggingAspect, anotherInstance);
    }

    @Test
    @DisplayName("Should have correct number of beans")
    public void testBeanCount() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        assertTrue(beanNames.length >= 3); // At least our 3 beans plus Spring infrastructure beans
    }

    @Test
    @DisplayName("Should have beans with correct names")
    public void testBeanNames() {
        assertTrue(applicationContext.containsBean("myService"));
        assertTrue(applicationContext.containsBean("consoleLoggerService"));
        assertTrue(applicationContext.containsBean("loggingAspect"));
    }

    @Test
    @DisplayName("Should be able to call service methods")
    public void testServiceMethodsAreCallable() {
        // This test verifies that the beans are properly wired and callable
        assertDoesNotThrow(() -> {
            myService.performAction("Integration test");
            myService.anotherAction();
        });
    }

    @Test
    @DisplayName("Should have LoggingAspect with properly injected LoggerService")
    public void testAspectDependencyInjection() {
        // The aspect should be created with the logger service dependency
        assertNotNull(loggingAspect);
        
        // We can verify this by calling the aspect method directly
        assertDoesNotThrow(() -> {
            loggingAspect.logBefore();
        });
    }
}