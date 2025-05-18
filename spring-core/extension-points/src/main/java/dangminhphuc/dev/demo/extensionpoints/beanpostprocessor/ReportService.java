package dangminhphuc.dev.demo.extensionpoints.beanpostprocessor;

public class ReportService implements Service {
    @Override
    public void execute() {
        System.out.println("Generating report");
    }
}
