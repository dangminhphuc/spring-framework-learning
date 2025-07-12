package com.dangminhphuc.dev.beans.advance.annotation;

public class DependsOnBean {
    private String message;

    public DependsOnBean(String message) {
        this.message = message;
        System.out.println(message);
    }
}
