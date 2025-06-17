package dangminhphuc.dev.demo.extensionpoints.beanfactorypostprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("dangminhphuc.dev.demo.extensionpoints.beanfactorypostprocessor")
public class AppConfiguration {

    @Bean
    public Greeting greeting() {
        return new Greeting()
                .setMessage("Hello, World!");
    }

}
