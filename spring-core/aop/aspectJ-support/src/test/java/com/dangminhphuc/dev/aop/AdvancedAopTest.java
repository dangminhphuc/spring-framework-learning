package com.dangminhphuc.dev.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Advanced AOP Tests")
public class AdvancedAopTest {

    @Mock
    private LoggerService mockLoggerService;

    private MyService proxiedService;
    private AdvancedLoggingAspect advancedAspect;

    @BeforeEach
    public void setUp() {
        MyService target = new MyService();
        this.mockLoggerService = mock(LoggerService.class);
        this.advancedAspect = new AdvancedLoggingAspect(mockLoggerService);

        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(advancedAspect);
        this.proxiedService = factory.getProxy();
    }

    @Test
    @DisplayName("Should log method entry and exit")
    public void testMethodEntryAndExit() {
        // When
        proxiedService.performAction("test");

        // Then
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockLoggerService, times(2)).log(messageCaptor.capture());
        
        List<String> capturedMessages = messageCaptor.getAllValues();
        assertTrue(capturedMessages.get(0).contains("Entering method"));
        assertTrue(capturedMessages.get(1).contains("Exiting method"));
    }

    @Test
    @DisplayName("Should handle method parameters in advice")
    public void testMethodParameterLogging() {
        // When
        proxiedService.performAction("testParam");

        // Then
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockLoggerService, atLeast(1)).log(messageCaptor.capture());
        
        boolean foundParameterLog = messageCaptor.getAllValues().stream()
            .anyMatch(msg -> msg.contains("testParam"));
        assertTrue(foundParameterLog);
    }

    @Test
    @DisplayName("Should measure method execution time")
    public void testExecutionTimeMeasurement() {
        // When
        proxiedService.performAction("timing-test");

        // Then
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockLoggerService, atLeast(1)).log(messageCaptor.capture());
        
        boolean foundTimingLog = messageCaptor.getAllValues().stream()
            .anyMatch(msg -> msg.contains("ms"));
        assertTrue(foundTimingLog);
    }

    @Test
    @DisplayName("Should handle exceptions in advised methods")
    public void testExceptionHandling() {
        // Given
        MyService faultyService = new FaultyService();
        AspectJProxyFactory factory = new AspectJProxyFactory(faultyService);
        factory.addAspect(advancedAspect);
        MyService proxiedFaultyService = factory.getProxy();

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            proxiedFaultyService.performAction("error");
        });

        // Verify exception was logged
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockLoggerService, atLeast(1)).log(messageCaptor.capture());
        
        boolean foundExceptionLog = messageCaptor.getAllValues().stream()
            .anyMatch(msg -> msg.contains("Exception"));
        assertTrue(foundExceptionLog);
    }

    // Advanced Aspect for testing
    @Aspect
    @Component
    public static class AdvancedLoggingAspect {
        private final LoggerService loggerService;

        public AdvancedLoggingAspect(LoggerService loggerService) {
            this.loggerService = loggerService;
        }

        @Pointcut("execution(* com.dangminhphuc.dev.aop.MyService.*(..))")
        public void serviceMethodExecution() {}

        @Around("serviceMethodExecution()")
        public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
            long startTime = System.currentTimeMillis();
            
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            loggerService.log("Entering method: " + methodName + " with args: " + java.util.Arrays.toString(args));
            
            try {
                Object result = joinPoint.proceed();
                long endTime = System.currentTimeMillis();
                loggerService.log("Exiting method: " + methodName + " - Execution time: " + (endTime - startTime) + "ms");
                return result;
            } catch (Exception e) {
                loggerService.log("Exception in method: " + methodName + " - " + e.getMessage());
                throw e;
            }
        }
    }

    // Faulty service for testing exception handling
    public static class FaultyService extends MyService {
        @Override
        public void performAction(String input) {
            if ("error".equals(input)) {
                throw new RuntimeException("Intentional test exception");
            }
            super.performAction(input);
        }
    }
}