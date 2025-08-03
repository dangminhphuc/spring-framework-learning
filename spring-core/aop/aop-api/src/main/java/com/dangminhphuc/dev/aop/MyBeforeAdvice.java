package com.dangminhphuc.dev.aop;

import com.dangminhphuc.dev.service.ConsoleLoggerService;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MyBeforeAdvice implements MethodBeforeAdvice {
    private final ConsoleLoggerService loggerService;

    public MyBeforeAdvice(ConsoleLoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        this.loggerService.log("Before method: " + method.getName() + " on target: " + target.getClass().getName());
    }
}
