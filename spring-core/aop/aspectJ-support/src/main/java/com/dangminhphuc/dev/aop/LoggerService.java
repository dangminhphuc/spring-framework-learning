package com.dangminhphuc.dev.aop;

public interface LoggerService {
    void log(String message);

    void abort(String message);
}