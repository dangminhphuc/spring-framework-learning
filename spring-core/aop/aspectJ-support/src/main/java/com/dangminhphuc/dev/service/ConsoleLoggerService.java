package com.dangminhphuc.dev.service;

import org.springframework.stereotype.Component;

@Component
public class ConsoleLoggerService implements LoggerService {
    @Override
    public void log(String message) {
        System.out.println(message);
    }

}