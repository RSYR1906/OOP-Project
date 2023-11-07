package org.sc2002.controller;

import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users;

    public UserController() {
        users = new ArrayList<>();
        users.add(new Student("student1", "student1@ntu.edu.sg" ,"password", "EEE"));
        users.add(new Student("student2", "student2@ntu.edu.sg","password", "SCSE"));
        users.add(new Staff("staff1", "staff1@ntu.edu.sg","admin123", "NBS"));
        users.add(new Staff("staff2", "staff2@ntu.edu.sg" ,"admin123", "SSS"));
    }

    public User authenticateUser(String userId, String password) {
        for (User user : users) {
            if (user.getID().equals(userId) && user.getPassword().equals(password)) {
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
