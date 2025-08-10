package com.dangminhphuc.dev.transaction;

import lombok.Data;

@Data
public class Foo {
    private String name;

    public Foo(String name) {
        this.name = name;
    }
}
