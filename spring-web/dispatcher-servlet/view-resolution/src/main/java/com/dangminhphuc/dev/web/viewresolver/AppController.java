package com.dangminhphuc.dev.web.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    // in ViewResolver example, controller only return view name,
    // ViewResolver will resolve it to actual view

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/hello-world")
    public String helloWorld(Model model) {
        model.addAttribute("name", "Dang Minh Phuc");
        return "hello-world";
    }

}
