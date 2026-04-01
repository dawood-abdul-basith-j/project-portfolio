package service;

import exception.AuthenticationFailedException;
import repository.UserRepository;
import model.User;
import util.IDGenerator;

import java.util.Optional;

public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String password) throws Exception {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("User with email already exists.");
        }
        User newUser = new User(IDGenerator.generateUserId(), name, email, password);
        userRepository.save(newUser);
        return newUser;
    }

    public User login(String email, String password) throws AuthenticationFailedException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt.get();
        }
        throw new AuthenticationFailedException("Invalid email or password.");
    }
}
