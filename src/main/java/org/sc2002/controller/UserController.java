package org.sc2002.controller;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users;

    public UserController() {
        users = new ArrayList<>();
        users.add(new Student("student1", "password", Faculty.EEE));
        users.add(new Student("student2", "password", Faculty.SCSE));
        users.add(new Staff("staff1", "admin123", Faculty.NBS));
        users.add(new Staff("staff2", "admin123", Faculty.SSS));
    }

    public User authenticateUser(String userId, String password) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public String getUserRole(User user) {
        if (user instanceof Staff) {
            return "Staff Member";
        } else if (user instanceof Student) {
            return "Student";
        } else {
            return "Unknown";
        }
    }
}
