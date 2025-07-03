package com.dangminhphuc.dev.methodvalidation;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class Bar {

    public String doSomething(@NotNull String str) {
        return str;
    }
}
