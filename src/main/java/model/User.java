package model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String id;
    private String name;
    private String password; // þetta ætti að vera hashed en er það ekki rn
    private String email;

    public User(String name, String password, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.password = Objects.requireNonNull(password, "Password cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
         // kannski bæta við email format check
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }

    // Setters 
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; } // Consider a dedicated changePassword method
    public void setEmail(String email) { this.email = email; }

    // sama equals check on í tour.java nema á notendum
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }


    // basic hash
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

     @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}