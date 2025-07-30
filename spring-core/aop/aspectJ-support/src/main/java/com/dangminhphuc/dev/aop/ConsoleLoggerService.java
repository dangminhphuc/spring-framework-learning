package com.dangminhphuc.dev.aop;

import org.springframework.stereotype.Component;

@Component
public class ConsoleLoggerService implements LoggerService {
    @Override
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public void abort(String message) {

    }
}