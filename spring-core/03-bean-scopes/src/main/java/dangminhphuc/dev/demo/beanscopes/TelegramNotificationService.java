package dangminhphuc.dev.demo.beanscopes;

public class TelegramNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending telegram notification from instance [" + this.hashCode() + "]: " + message);
    }
}
