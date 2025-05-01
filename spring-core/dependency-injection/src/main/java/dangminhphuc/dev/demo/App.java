package dangminhphuc.dev.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

        BookService service = context.getBean("bookService", BookService.class);
        service.addBook(new Book("Java Core", "<NAME>"));
        service.addBook(new Book("Spring Core", "<NAME>"));
        service.listBooks();
    }
}
