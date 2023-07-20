package user;

import common.BaseRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class UserRepository extends BaseRepository<User, UUID> {
    private static final UserRepository userRepository = new UserRepository();
    public Optional<User> findByPhoneNumber(String phoneNumber){
        return entities.stream()
                .filter(user1 -> user1.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }
    public static UserRepository getInstance() {
        return userRepository;
    }
}
