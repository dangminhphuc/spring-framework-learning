package dangminhphuc.dev.demo.extensionpoints.beanpostprocessor;

public class NotificationService implements Service {
    @Override
    public void execute() {
        System.out.println("Sending notification");
    }
}
