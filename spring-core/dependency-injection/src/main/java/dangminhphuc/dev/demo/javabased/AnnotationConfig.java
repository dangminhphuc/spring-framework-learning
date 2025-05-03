package dangminhphuc.dev.demo.javabased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("dangminhphuc.dev.demo.annotation")
public class AnnotationConfig {

    @Bean
    public List<User> users() {
        return new ArrayList<User>() {
            {
                add(new User("dangminhphuc.init01"));
                add(new User("dangminhphuc.init02"));
            }
        };
    }

    @Bean
    public UserRepository userRepository(List<User> users) {
        return new UserRepository(users);
    }
}
