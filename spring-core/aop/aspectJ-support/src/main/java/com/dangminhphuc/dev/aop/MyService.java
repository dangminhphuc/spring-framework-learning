package com.dangminhphuc.dev.aop;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void performAction(String input) {
        String s = "Action performed with input: " + input;
        System.out.println(s);
    }

    public void anotherAction() {
        System.out.println("Another action performed!");
    }
}
