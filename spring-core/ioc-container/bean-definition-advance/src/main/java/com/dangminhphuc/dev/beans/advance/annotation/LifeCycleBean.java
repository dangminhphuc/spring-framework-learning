package com.dangminhphuc.dev.beans.advance.annotation;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component("lifecycleBean")
public class LifeCycleBean {
    // a variable used to capture current state info: init, post construct & pre destroy
    private final List<String> states = new ArrayList<>();

    public LifeCycleBean() {
        this.states.add("constructor");
    }

    @PostConstruct
    public void onInit() {
        this.states.add("postConstruct");
    }

    @PreDestroy
    public void onDestroy() {
        this.states.add("preDestroy");
    }
}
