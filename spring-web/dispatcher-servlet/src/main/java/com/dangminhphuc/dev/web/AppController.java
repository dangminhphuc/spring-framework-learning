package com.dangminhphuc.dev.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello from AppController";
    }

    @GetMapping("/exception/500")
    public String exception500() {
        throw new RuntimeException("Exception from AppController");
    }
}
