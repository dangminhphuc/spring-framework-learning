package dangminhphuc.dev.demo.annotation;

import org.springframework.stereotype.Component;

@Component
public class Engine {
    public void start() {
        System.out.println("Engine start");
    }
}
