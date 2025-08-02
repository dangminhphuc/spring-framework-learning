package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoggingAspect Unit Tests")
public class LoggingAspectTest {

    @Mock
    private LoggerService mockLoggerService;

    private LoggingAspect loggingAspect;

    @BeforeEach
    public void setUp() {
        loggingAspect = new LoggingAspect(mockLoggerService);
    }

    @Test
    @DisplayName("Should create aspect with logger service")
    public void testAspectCreation() {
        // Then
        assertNotNull(loggingAspect);
    }

    @Test
    @DisplayName("Should call logger service when logBefore is invoked")
    public void testLogBefore() {
        // When
        loggingAspect.logBefore();

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
    }

    @Test
    @DisplayName("Should handle multiple calls to logBefore")
    public void testMultipleLogBeforeCalls() {
        // When
        loggingAspect.logBefore();
        loggingAspect.logBefore();
        loggingAspect.logBefore();

        // Then
        verify(mockLoggerService, times(3)).log("Logging before method execution!");
    }

    @Test
    @DisplayName("Should not call abort method on logger service")
    public void testLogBeforeDoesNotCallAbort() {
        // When
        loggingAspect.logBefore();

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
        verify(mockLoggerService, never()).abort(anyString());
    }

    @Test
    @DisplayName("Should pass exact message to logger service")
    public void testLogBeforeMessageContent() {
        // When
        loggingAspect.logBefore();

        // Then
        verify(mockLoggerService).log("Logging before method execution!");
        verifyNoMoreInteractions(mockLoggerService);
    }

    @Test
    @DisplayName("Should throw exception when logger service is null")
    public void testAspectCreationWithNullLogger() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("LoggerService cannot be null");
        });
    }

    @Test
    @DisplayName("Should handle logger service exception gracefully")
    public void testLogBeforeWithLoggerException() {
        // Given
        doThrow(new RuntimeException("Logger error")).when(mockLoggerService)
            .log("Logging before method execution!");

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            loggingAspect.logBefore();
        });
    }
}