package repository;

import model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private Map<String, User> userDb = new HashMap<>();

    public void save(User user) {
        userDb.put(user.getUserId(), user);
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable(userDb.get(userId));
    }

    public Optional<User> findByEmail(String email) {
        return userDb.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
