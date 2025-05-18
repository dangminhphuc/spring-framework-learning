package com.dangminhphuc.dev.configuration.xml.core;


import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

// Implement Factory bean by implement FactoryBean interface
public class EmployeeFactory implements FactoryBean<List<Employee>> {

    @Override
    public List<Employee> getObject() throws Exception {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee().setName("Paul"));
        employees.add(new Employee().setName("Fuck"));
        employees.add(new Employee().setName("Damn"));

        return employees;
    }

    @Override
    public Class<?> getObjectType() {
        return Class.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
