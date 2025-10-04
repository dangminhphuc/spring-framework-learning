package com.dangminhphuc.dev.web.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Controller
public class WebController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome(Locale locale) {
        return this.messageSource.getMessage("welcome.message", null, locale);
    }
}
