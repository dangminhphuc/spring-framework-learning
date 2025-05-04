package dangminhphuc.dev.demo.beanscopes.prototype;

import dangminhphuc.dev.demo.beanscopes.NotificationService;

public class TelegramNotificationService implements NotificationService {

    public void init() {
        System.out.println("Initializing telegram notification service");
    }

    @Override
    public void send(String message) {
        System.out.println("Sending telegram notification: " + message);
    }

    public void destroy() {
        System.out.println("Destroying telegram notification service");
    }
}
