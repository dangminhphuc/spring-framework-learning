package dangminhphuc.dev.demo.javabased;

import java.util.List;

//@Repository
public class UserRepository {
    private final List<User> users;

    public UserRepository(List<User> users) {
        this.users = users;
    }

    public List<User> users() {
        return this.users;
    }

    public void add(User user) {
        this.users.add(user);
    }
}
