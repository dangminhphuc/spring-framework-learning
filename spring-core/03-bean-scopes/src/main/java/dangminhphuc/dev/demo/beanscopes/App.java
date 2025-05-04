package dangminhphuc.dev.demo.beanscopes;

import dangminhphuc.dev.demo.beanscopes.prototype.TelegramNotificationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        NotificationService emailService = context.getBean("emailService", NotificationService.class);
        for (int i = 0; i < 3; i++) {
            NotificationService bean = context.getBean("emailService", NotificationService.class);
            if (emailService != bean) {
                System.out.println("Not same instance");
                continue;
            }
            System.out.println("Same instance");
        }

        System.out.println("---");

        NotificationService telegramService = context.getBean("telegramService", TelegramNotificationService.class);
        for (int i = 0; i < 3; i++) {
            TelegramNotificationService bean = context.getBean("telegramService", TelegramNotificationService.class);
            if (telegramService != bean) {
                System.out.println("Every time get a new instance");
                continue;
            }
            System.out.println("Same instance");
        }


    }
}
