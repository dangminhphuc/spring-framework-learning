package dangminhphuc.dev.demo.extensionpoints.beanfactorypostprocessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Greeting greeting = context.getBean("greeting", Greeting.class);
        System.out.println(greeting);
    }
}
