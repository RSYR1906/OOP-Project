package org.sc2002.controller;

import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.entity.User;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User controller.
 */
public class UserController {
    private List<User> users;
    private StudentRepository studentRepository;
    private StaffRepository staffRepository;

    /**
     * Instantiates a new User controller.
     */
    public UserController() {
        users = new ArrayList<>();

    }

    /**
     * Instantiates a new User controller.
     *
     * @param studentRepository the student repository
     * @param staffRepository   the staff repository
     */
    public UserController(StudentRepository studentRepository, StaffRepository staffRepository) {
        users = new ArrayList<>();
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
        users.addAll(studentRepository.getAllStudents());
        users.addAll(staffRepository.getAllStaff());
    }

    /**
     * Authenticate user user.
     *
     * @param userId   the user id
     * @param password the password
     * @return the user
     */
    public User authenticateUser(String userId, String password) {
        for (User user : users) {
            //System.out.println(user.getID()+" "+user.getPassword());
            if (user.getID().equalsIgnoreCase(userId) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets user role.
     *
     * @param user the user
     * @return the user role
     */
    public String getUserRole(User user) {
        if (user instanceof Staff) {
            return "Staff Member";
        } else if (user instanceof Student) {
            return "Student";
        } else {
            return "Unknown";
        }
    }

    /**
     * Change password boolean.
     *
     * @param authenticatedUser the authenticated user
     * @param newPassword       the new password
     * @return the boolean
     */
    public boolean changePassword(User authenticatedUser, String newPassword){
        authenticatedUser.setPassword(newPassword);
        String role = this.getUserRole(authenticatedUser);
        if(role.equals("Staff Member")){
            staffRepository.saveStaff((Staff) authenticatedUser);
        }else if(role.equals("Student"))
            studentRepository.saveStudent((Student) authenticatedUser);
        else
            return false;
        return true;
    }
}
