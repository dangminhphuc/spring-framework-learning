package dangminhphuc.dev.demo;

import dangminhphuc.dev.demo.annotation.AnnotationConfig;
import dangminhphuc.dev.demo.annotation.Car;
import dangminhphuc.dev.demo.javabased.JavaBasedConfig;
import dangminhphuc.dev.demo.javabased.User;
import dangminhphuc.dev.demo.javabased.UserService;
import dangminhphuc.dev.demo.xml.Book;
import dangminhphuc.dev.demo.xml.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("config.xml");
        BookService bookService = xmlContext.getBean("bookService", BookService.class);
        bookService.addBook(new Book("Java Core", "<AUTH_1>"));
        bookService.addBook(new Book("Spring Core", "<AUTH_2>"));
        bookService.listBooks();

        System.out.println("---");

        ApplicationContext javaContext = new AnnotationConfigApplicationContext(JavaBasedConfig.class);
        UserService userService = javaContext.getBean(UserService.class);
        userService.addUser(new User("dangminhphuc"));
        userService.addUser(new User("dangminhphuc.dev"));
        userService.allUser();

        System.out.println("---");

        new AnnotationConfigApplicationContext(AnnotationConfig.class)
                .getBean(Car.class)
                .start();
    }
}
