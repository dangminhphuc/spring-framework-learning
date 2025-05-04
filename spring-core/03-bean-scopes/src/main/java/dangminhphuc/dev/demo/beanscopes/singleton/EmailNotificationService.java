package dangminhphuc.dev.demo.beanscopes.singleton;

import dangminhphuc.dev.demo.beanscopes.NotificationService;

public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending email notification from instance [" + this.hashCode() + "]: " + message);
    }
}
