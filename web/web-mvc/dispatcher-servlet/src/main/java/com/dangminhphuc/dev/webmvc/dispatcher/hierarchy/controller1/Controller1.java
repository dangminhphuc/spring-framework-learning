package com.dangminhphuc.dev.webmvc.dispatcher.hierarchy.controller1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dangminhphuc.dev.webmvc.dispatcher.hierarchy.service.MyService;

@RestController
public class Controller1 {

    @Autowired
    private MyService myService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from app1: " + myService.getMessage();
    }
}
