package user;

public class UserService {
    public static final UserService userService = new UserService();
    private final UserRepository userRepository = UserRepository.getInstance();
    public static UserService getInstance() {
        return userService;
    }
}
