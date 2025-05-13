package dangminhphuc.dev.demo.extensionpoints.beanpostprocessor;

public class EmailService implements Service {
    @Override
    public void execute() {
        System.out.println("Sending email");
    }
}
