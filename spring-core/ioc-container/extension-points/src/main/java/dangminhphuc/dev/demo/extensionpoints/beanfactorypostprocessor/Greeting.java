package dangminhphuc.dev.demo.extensionpoints.beanfactorypostprocessor;

public class Greeting {
    private String message;

    public String getMessage() {
        return message;
    }

    public Greeting setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
