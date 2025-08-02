package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@DisplayName("AOP Integration Tests")
public class AopTest {
    private MyService proxiedService;
    private LoggerService mockLoggerService;

    @BeforeEach
    public void setUpStreams() {
        // 1. Create the target object
        MyService target = new MyService();

        // 2. Create the mock collaborator
        this.mockLoggerService = mock(LoggerService.class);

        // 3. Create the aspect instance, injecting the mock
        LoggingAspect aspect = new LoggingAspect(mockLoggerService);

        // 4. Create the proxy factory
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(aspect);

        // 5. Get the proxied object
        this.proxiedService = factory.getProxy();
    }

    @Test
    @DisplayName("Should log before method execution when performAction is called")
    public void testAopLoggingOnPerformAction() {
        // When
        this.proxiedService.performAction("TEST");

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
    }

    @Test
    @DisplayName("Should log before method execution when anotherAction is called")
    public void testAopLoggingOnAnotherAction() {
        // When
        this.proxiedService.anotherAction();

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
    }

    @Test
    @DisplayName("Should log before method execution for multiple calls")
    public void testAopLoggingOnMultipleCalls() {
        // When
        this.proxiedService.performAction("TEST1");
        this.proxiedService.anotherAction();
        this.proxiedService.performAction("TEST2");

        // Then
        verify(mockLoggerService, times(3)).log("Logging before method execution!");
    }

    @Test
    @DisplayName("Should call logger service with correct message format")
    public void testLoggerServiceMessageFormat() {
        // When
        this.proxiedService.performAction("INPUT");

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
        verifyNoMoreInteractions(mockLoggerService);
    }

    @Test
    @DisplayName("Should handle null input to performAction")
    public void testAopLoggingWithNullInput() {
        // When
        this.proxiedService.performAction(null);

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
    }

    @Test
    @DisplayName("Should handle empty string input to performAction")
    public void testAopLoggingWithEmptyInput() {
        // When
        this.proxiedService.performAction("");

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
    }
}
