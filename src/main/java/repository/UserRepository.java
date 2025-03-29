package repository;
import model.User;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private List<User> users = new ArrayList<>();


    public void addUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User má ekki vera null");
        }
        if(findUserByName(user.getName()) != null) {
            throw new IllegalArgumentException("notandi með nafnið '" + user.getName() + "' er nú þegar til");
        }
        users.add(user);
    }

    
    public User findUserByName(String name) {
        if(name == null) {
            return null;
        }
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}