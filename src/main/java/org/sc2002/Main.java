package org.sc2002;

import org.sc2002.boundary.LoginUI;
import org.sc2002.boundary.StaffUI;
import org.sc2002.boundary.StudentUI;
import org.sc2002.controller.*;
import org.sc2002.entity.*;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.CampStudentRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository();
        StaffRepository staffRepository = new StaffRepository();
        CampRepository campRepository = new CampRepository();
        CampStudentRepository campStudentRepository = new CampStudentRepository(campRepository, studentRepository);

        campRepository.load();
        studentRepository.load();
        staffRepository.load();
        campStudentRepository.load();

        CampController campController = new CampController(campRepository);
        UserController userController = new UserController(studentRepository, staffRepository);
        StaffController staffController = new StaffController(campController, staffRepository);
        StudentController studentController = new StudentController(campController, campStudentRepository);
        EnquiryController enquiryController = new EnquiryController();

        LoginUI loginUI = new LoginUI(userController);

        User user = loginUI.body();
        if (userController.getUserRole(user).equals("Staff Member")) {
            Staff staff = (Staff) user;
            StaffUI staffUI = new StaffUI(staff, staffController, campController);
            staffUI.body();
        } else if (userController.getUserRole(user).equals("Student")) {
            Student student = (Student) user;
            StudentUI studentUI = new StudentUI(student, studentController, enquiryController,campController);
            studentUI.body();
        }
    }
}
