package com.dangminhphuc.dev.beans.advance.java;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;

@Getter
public class LifecycleBean {

    public LifecycleBean() {
    }

    @PostConstruct
    public void postConstruct() {
    }

    @PreDestroy
    public void preDestroy() {
    }
}
