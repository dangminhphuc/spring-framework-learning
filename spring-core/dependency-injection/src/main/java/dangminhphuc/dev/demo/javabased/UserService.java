package dangminhphuc.dev.demo.javabased;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void addUser(User user) {
        this.repo.add(user);
    }

    public void allUser() {
        this.repo.users().forEach(System.out::println);
    }
}
