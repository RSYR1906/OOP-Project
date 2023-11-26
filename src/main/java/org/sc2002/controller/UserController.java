package org.sc2002.controller;

import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.entity.User;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users;

    public UserController(StudentRepository studentRepository, StaffRepository staffRepository) {
        users = new ArrayList<>();
        users.addAll(studentRepository.getAllStudents());
        users.addAll(staffRepository.getAllStaff());
    }

    public User authenticateUser(String userId, String password) {
        for (User user : users) {
            //System.out.println(user.getID()+" "+user.getPassword());
            if (user.getID().equalsIgnoreCase(userId) && user.getPassword().equals(password)) {
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
