package com.dangminhphuc.dev.configuration;

import com.dangminhphuc.dev.configuration.xml.core.Company;
import com.dangminhphuc.dev.configuration.xml.core.Department;
import com.dangminhphuc.dev.configuration.xml.core.Employee;
import com.dangminhphuc.dev.configuration.xml.core.Position;
import com.dangminhphuc.dev.configuration.xml.service.CompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XMLConfigurationTest {

    @BeforeEach
    public void beforeExecute(TestInfo testInfo) {
        System.out.println("--- " + testInfo.getDisplayName() + " ---");
    }

    @AfterEach
    public void afterExecute() {
        System.out.println();
    }

    @Test
    public void testAutowiring() {
        ApplicationContext context = new ClassPathXmlApplicationContext("autowiring.xml");

        Department department = context.getBean("department", Department.class);
        assertEquals("IT Department", department.getName(), "Department name should be IT Department");

        Position position = context.getBean("position", Position.class);
        assertEquals("Software Engineer", position.getTitle(), "Position title should be Software Engineer");

        Employee employee = context.getBean("employee", Employee.class);
        assertEquals("Paul", employee.getName(), "employee name should be Paul");
        assertEquals(department, employee.getDepartment(), "Department should be IT Department");
        assertEquals(position, employee.getPosition(), "Position should be Software Engineer");

        Company company = context.getBean("company", Company.class);
        assertEquals(employee, company.getEmployee(), "Employee should be Paul");
    }

    @Test
    public void testLifeCycle() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("lifecycle.xml");
        Company company = context.getBean("company", Company.class);
        assertEquals("DMP Corp", company.getName(), "Company name should be DMP Corp");

        // ensure destroy method called
        context.close();
    }

    @Test
    public void testFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("factory-bean.xml");
        CompanyService companyService = context.getBean("companyService", CompanyService.class);
        assertNotNull(companyService.getEmployees(), "Employees should not be null");

        companyService.print();
    }


    @Test
    public void testInherited() {
        ApplicationContext context = new ClassPathXmlApplicationContext("inheritance.xml");
        Company company = context.getBean("company", Company.class);
        assertEquals("Paul", company.getEmployee().getName(), "Employees should not be Paul");
    }
}
