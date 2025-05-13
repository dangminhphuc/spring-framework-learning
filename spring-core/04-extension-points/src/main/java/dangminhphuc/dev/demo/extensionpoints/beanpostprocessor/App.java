package dangminhphuc.dev.demo.extensionpoints.beanpostprocessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        context.getBean("emailService", EmailService.class).execute();
        context.getBean("reportService", ReportService.class).execute();
        context.getBean("notificationService", NotificationService.class).execute();
    }
}
