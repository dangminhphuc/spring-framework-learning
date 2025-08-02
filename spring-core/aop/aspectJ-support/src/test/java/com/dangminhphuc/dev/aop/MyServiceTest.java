package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MyService Unit Tests")
public class MyServiceTest {
    
    private MyService myService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        myService = new MyService();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should perform action with valid input")
    public void testPerformActionWithValidInput() {
        // Given
        String input = "test input";
        String expectedOutput = "Action performed with input: " + input;

        // When
        myService.performAction(input);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Should perform action with null input")
    public void testPerformActionWithNullInput() {
        // Given
        String input = null;
        String expectedOutput = "Action performed with input: null";

        // When
        myService.performAction(input);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Should perform action with empty input")
    public void testPerformActionWithEmptyInput() {
        // Given
        String input = "";
        String expectedOutput = "Action performed with input: ";

        // When
        myService.performAction(input);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Should perform action with special characters")
    public void testPerformActionWithSpecialCharacters() {
        // Given
        String input = "test@#$%^&*()_+";
        String expectedOutput = "Action performed with input: " + input;

        // When
        myService.performAction(input);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Should perform another action")
    public void testAnotherAction() {
        // Given
        String expectedOutput = "Another action performed!";

        // When
        myService.anotherAction();

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Should handle multiple method calls")
    public void testMultipleMethodCalls() {
        // Given
        String input1 = "first";
        String input2 = "second";

        // When
        myService.performAction(input1);
        myService.anotherAction();
        myService.performAction(input2);

        // Then
        String actualOutput = outputStreamCaptor.toString();
        assertTrue(actualOutput.contains("Action performed with input: first"));
        assertTrue(actualOutput.contains("Another action performed!"));
        assertTrue(actualOutput.contains("Action performed with input: second"));
    }

    @Test
    @DisplayName("Should handle long input strings")
    public void testPerformActionWithLongInput() {
        // Given
        String longInput = "a".repeat(1000);
        String expectedOutput = "Action performed with input: " + longInput;

        // When
        myService.performAction(longInput);

        // Then
        String actualOutput = outputStreamCaptor.toString().trim();
        assertEquals(expectedOutput, actualOutput);
    }
}