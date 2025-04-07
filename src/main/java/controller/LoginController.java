package controller;

import model.User;
import repository.UserRepository; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Objects; 

public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserRepository userRepository; // Heldur í UserRepository

    // Constructor tekur UserRepository
    public LoginController(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository cannot be null");
    }

    // login með password og username
    public Optional<User> login(String username, String password) {
        if (username == null || password == null) {
             log.warn("Login attempt with null username or password.");
             return Optional.empty();
        }

        Optional<User> userOpt = userRepository.findUserByName(username); 

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // comperar raw password, ekki hashed
            // Í alvöru kerfi þarf að nota password hashing hér 
            if (user.getPassword().equals(password)) {
                log.info("User '{}' logged in successfully.", username);
                return Optional.of(user); // Skila notandanum ef lykilorð passar
            } else {
                log.warn("Failed login attempt for user '{}': Invalid password.", username);
            }
        } else {
             log.warn("Failed login attempt: User '{}' not found.", username);
        }
        // Ef notandi fannst ekki eða lykilorð var rangt
        return Optional.empty();
    }


    // register sem ég veit eki hvort við eigum að gera, email, username, password
    // með error höndlun
    public User register(String name, String password, String email) throws IllegalArgumentException {
        
        if (name == null || name.trim().isEmpty() || password == null || password.isEmpty() || email == null || !email.contains("@")) {
             throw new IllegalArgumentException("Invalid user data provided for registration.");
        }

        
        User newUser = new User(name.trim(), password, email.trim()); 

        // Reyna að bæta við notanda í gegnum repository
        try {
            userRepository.addUser(newUser); 
            log.info("User registered successfully: {}", newUser);
            return newUser; // Skila nýskráðum notanda
        } catch (IllegalArgumentException e) {
            log.error("Registration failed for user '{}': {}", name, e.getMessage());
            throw e; 
        }
    }
}