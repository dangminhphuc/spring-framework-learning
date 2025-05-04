package dangminhphuc.dev.demo.beanscopes.singleton;

import dangminhphuc.dev.demo.beanscopes.NotificationService;

public class EmailNotificationService implements NotificationService {

    public void init() {
        System.out.println("Initializing email notification service");
    }

    @Override
    public void send(String message) {
        System.out.println("Sending email notification: " + message);
    }

    public void destroy() {
        System.out.println("Destroying email notification service");
    }
}
