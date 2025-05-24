package com.dangminhphuc.dev.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        HelloPublisher helloPublisher = context.getBean("helloPublisher", HelloPublisher.class);
        helloPublisher.sayHello();
    }
}
