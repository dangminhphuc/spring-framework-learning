package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConsoleLoggerService Unit Tests")
public class ConsoleLoggerServiceTest {

    private ConsoleLoggerService consoleLoggerService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        consoleLoggerService = new ConsoleLoggerService();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should log message to console")
    public void testLogMessage() {
        // Given
        String message = "Test log message";

        // When
        consoleLoggerService.log(message);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(message, actualOutput);
    }

    @Test
    @DisplayName("Should log null message")
    public void testLogNullMessage() {
        // Given
        String message = null;

        // When
        consoleLoggerService.log(message);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals("null", actualOutput);
    }

    @Test
    @DisplayName("Should log empty message")
    public void testLogEmptyMessage() {
        // Given
        String message = "";

        // When
        consoleLoggerService.log(message);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals("", actualOutput);
    }

    @Test
    @DisplayName("Should log message with special characters")
    public void testLogMessageWithSpecialCharacters() {
        // Given
        String message = "Test message with @#$%^&*()_+ special chars";

        // When
        consoleLoggerService.log(message);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(message, actualOutput);
    }

    @Test
    @DisplayName("Should log multiple messages")
    public void testLogMultipleMessages() {
        // Given
        String message1 = "First message";
        String message2 = "Second message";

        // When
        consoleLoggerService.log(message1);
        consoleLoggerService.log(message2);

        // Then
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains(message1));
        assertTrue(actualOutput.contains(message2));
    }

    @Test
    @DisplayName("Should log long message")
    public void testLogLongMessage() {
        // Given
        String longMessage = "A".repeat(1000);

        // When
        consoleLoggerService.log(longMessage);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(longMessage, actualOutput);
    }

    @Test
    @DisplayName("Should log message with line breaks")
    public void testLogMessageWithLineBreaks() {
        // Given
        String messageWithLineBreaks = "Line 1\nLine 2\nLine 3";

        // When
        consoleLoggerService.log(messageWithLineBreaks);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(messageWithLineBreaks, actualOutput);
    }

    @Test
    @DisplayName("Should not do anything when abort is called")
    public void testAbortMethod() {
        // Given
        String message = "Abort message";

        // When
        consoleLoggerService.abort(message);

        // Then
        String actualOutput = outputStreamCaptor.toString();
        assertEquals("", actualOutput);
    }

    @Test
    @DisplayName("Should handle abort with null message")
    public void testAbortWithNullMessage() {
        // When
        consoleLoggerService.abort(null);

        // Then
        String actualOutput = outputStreamCaptor.toString();
        assertEquals("", actualOutput);
    }

    @Test
    @DisplayName("Should implement LoggerService interface")
    public void testImplementsLoggerService() {
        // Then
        assertTrue(consoleLoggerService instanceof LoggerService);
    }
}