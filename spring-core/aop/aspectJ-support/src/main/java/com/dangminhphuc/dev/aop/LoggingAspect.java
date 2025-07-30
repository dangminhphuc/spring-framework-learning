package com.dangminhphuc.dev.aop;

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

    @Pointcut("execution(* com.dangminhphuc.dev.aop.MyService.*(..))")
    public void myServiceMethods() {
    }

    @Before("myServiceMethods()")
    public void logBefore() {
        this.loggerService.log("Logging before method execution!");
    }
}
