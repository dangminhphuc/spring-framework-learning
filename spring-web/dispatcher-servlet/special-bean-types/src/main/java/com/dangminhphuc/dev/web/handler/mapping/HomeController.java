package com.dangminhphuc.dev.web.handler.mapping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    @ResponseBody
    public String root() {
        return "<html><head><title>Home Page</title></head><body><h1>Hello World - Root Path</h1></body></html>";
    }

    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "<html><head><title>Home Page</title></head><body><h1>Hello from Special Bean Types!</h1></body></html>";
    }
}
