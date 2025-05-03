package dangminhphuc.dev.demo;

import dangminhphuc.dev.demo.javabased.AnnotationConfig;
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

        ApplicationContext annotationContext = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        UserService userService = annotationContext.getBean(UserService.class);
        userService.addUser(new User("dangminhphuc"));
        userService.addUser(new User("dangminhphuc.dev"));
        userService.allUser();
    }
}
