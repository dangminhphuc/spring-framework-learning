package dangminhphuc.dev.demo.javabased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JavaBasedConfig {

    @Bean
    public UserRepository userRepository() {
        List<User> users = new ArrayList<User>() {
            {
                add(new User("dangminhphuc.init01"));
                add(new User("dangminhphuc.init02"));
            }
        };
        return new UserRepository(users);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
