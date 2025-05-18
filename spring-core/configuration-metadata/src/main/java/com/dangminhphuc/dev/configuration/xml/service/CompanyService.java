package com.dangminhphuc.dev.configuration.xml.service;

import com.dangminhphuc.dev.configuration.xml.core.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
public class CompanyService {
    @Setter
    @Getter
    private List<Employee> employees;

    public void print() {
        this.employees.forEach(System.out::println);
    }
}
