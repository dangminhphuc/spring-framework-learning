package dangminhphuc.dev.demo.beanscopes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//        EmailNotificationService emailService = context.getBean(EmailNotificationService.class);
//        emailService.send("Hello, this is a notification!");

        for (int i = 0; i < 3; i++) {
            NotificationService notificationService = context.getBean("emailService",NotificationService.class);
            notificationService.send("Hello, this is a notification!");
        }


        System.out.println("---");

        for (int i = 0; i < 3; i++) {
            NotificationService telegramService = context.getBean("telegramService",TelegramNotificationService.class);
            telegramService.send("Hello, this is a notification!");
        }


    }
}
