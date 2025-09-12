package com.dangminhphuc.dev.transaction;

import lombok.Data;

@Data
public class Bar {
    private String name;

    public Bar(String name) {
        this.name = name;
    }
}
