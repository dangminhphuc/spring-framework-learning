package dangminhphuc.dev.demo.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    private final Engine engine;
    private final Wheel wheel;

    @Autowired
    public Car(Engine engine, Wheel wheel) {
        this.engine = engine;
        this.wheel = wheel;
    }

    public void start() {
        this.engine.start();
        this.wheel.spin();
        System.out.println("Car start");
    }
}
