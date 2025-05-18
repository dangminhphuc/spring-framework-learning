package com.dangminhphuc.dev.configuration.xml.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString(of = "name")
@NoArgsConstructor
@Accessors(chain = true)
public class Employee {
    private String name;
    private Department department;
    private Position position;

    public Employee(Position position) {
        this.position = position;
    }
}
