package dangminhphuc.dev.demo.annotation;

import org.springframework.stereotype.Component;

@Component
public class Wheel {
    public void spin() {
        System.out.println("Wheel spin");
    }
}
