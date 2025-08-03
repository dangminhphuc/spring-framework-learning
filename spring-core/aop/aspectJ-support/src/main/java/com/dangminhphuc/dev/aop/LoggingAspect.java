package com.dangminhphuc.dev.aop;

import com.dangminhphuc.dev.service.LoggerService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final LoggerService loggerService;

    @Autowired
    public LoggingAspect(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Pointcut("execution(* com.dangminhphuc.dev.MyService.*(..))")
    public void performActionPointcut() {
        // The method decorated with @Pointcut is not meant to be executed.
        // Its body is always empty and should be ignored.
    }

    @Before("performActionPointcut()")
    public void logBefore() {
        this.loggerService.log("Logging before method execution!");
    }

    @After("performActionPointcut()")
    public void logAfter() {
        this.loggerService.log("Logging after method execution!");
    }
}
