package dangminhphuc.dev.demo.beanscopes.prototype;

import dangminhphuc.dev.demo.beanscopes.NotificationService;

public class TelegramNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending telegram notification from instance [" + this.hashCode() + "]: " + message);
    }
}
