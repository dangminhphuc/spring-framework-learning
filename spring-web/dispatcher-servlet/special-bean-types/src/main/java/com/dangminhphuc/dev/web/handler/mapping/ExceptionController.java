package com.dangminhphuc.dev.web.handler.mapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionController {

    @GetMapping("/exception/400")
    @ResponseBody
    public String ex400() {
        throw new IllegalArgumentException();
    }

    @GetMapping("/exception/500")
    @ResponseBody
    public String ex500() {
        throw new RuntimeException();
    }
}
