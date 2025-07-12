package com.dangminhphuc.dev.beans.advance.xml;

public class DependsOnBean {
    private String message;

    public DependsOnBean(String message) {
        this.message = message;
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }
}
