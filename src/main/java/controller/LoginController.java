package controller;

import model.User;
import repository.UserRepository;

public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        User user = userRepository.findUserByName(username);
        return user != null && user.getPassword().equals(password);
    }

    public void register(User user) {
        userRepository.addUser(user);
        System.out.println("User registered: " + user.getName());
    }
}