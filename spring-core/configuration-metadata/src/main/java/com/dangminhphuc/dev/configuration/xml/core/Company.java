package com.dangminhphuc.dev.configuration.xml.core;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private String name;
    private Employee employee;

    public void init() {
        System.out.println("Company initialized: " + this.name);
    }

    public void destroy() {
        System.out.println("Company destroyed: " + this.name);
    }
}
