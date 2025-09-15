package com.dangminhphuc.dev.webmvc.dispatcher.hierarchy.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dangminhphuc.dev.webmvc.dispatcher.hierarchy.service.MyService;

@RestController
public class Controller2 {

    @Autowired
    private MyService myService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from app2: " + myService.getMessage();
    }
}
