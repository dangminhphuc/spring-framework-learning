package com.dangminhphuc.dev.service;

public class ConsoleLoggerService implements LoggerService {
    @Override
    public void log(String message) {
        System.out.println("ConsoleLoggerService: " + message);
    }
}
