package repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList; 

import model.User; 

public class UserRepository {
    private final List<User> users = new CopyOnWriteArrayList<>();

    // Hér er hægt að bæta við mock notendum ef þarf til að prófa
    public UserRepository() {
        // user sem passar við það sem ég gerði
         try {
             addUser(new User("testuser", "password123", "test@example.com"));
             addUser(new User("admin", "adminpass", "admin@example.com"));
             System.out.println("UserRepository initialized with mock users.");
         } catch (IllegalArgumentException e) {
             System.err.println("Error initializing mock users: " + e.getMessage());
         }
    }

    public void addUser(User user) {
        Objects.requireNonNull(user, "User cannot be null"); 
        // Athuga hvort notandi sé þegar til (case-insensitive nafn eða email)
        if (findUserByName(user.getName()).isPresent()) {
            throw new IllegalArgumentException("Username '" + user.getName() + "' already exists.");
        }
         if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))) {
             throw new IllegalArgumentException("Email '" + user.getEmail() + "' already exists.");
         }
        users.add(user);
    }

    // Notaði Optional til að gefa skýrt til kynna að notandi finnist kannski ekki
    public Optional<User> findUserByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name)) // ekki case-sensitive leit
                .findFirst();
    }

     // finna eftir id
     public Optional<User> findUserById(String userId) {
        if (userId == null) {
            return Optional.empty();
        }
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst();
     }

     // Gætum bætt við:
     // public List<User> getAllUsers() { return new ArrayList<>(users); } 
     // public boolean removeUser(String userId) { ... }
}