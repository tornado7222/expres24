package user;

import java.util.List;
import java.util.Optional;

public class UserService {
    public static final UserService userService = new UserService();
    private final UserRepository userRepository = UserRepository.getInstance();
    public boolean create(User user){
        Optional<User> existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (existingUser.isEmpty()){
            userRepository.add(user);
            return true;
        }
        return false;
    }
    public static UserService getInstance() {
        return userService;
    }

    public User signIn(String phoneNumber, String password) {
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if (existingUser.isEmpty()){
            return null;
        }else {
            User user = existingUser.get();
            if (user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public List<User> getCook() {
        return userRepository.getCook();
    }

    public List<User> getCourier() {
        return userRepository.getCourier();
    }
}
