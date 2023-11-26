package org.sc2002;

import org.sc2002.boundary.LoginUI;
import org.sc2002.boundary.StaffUI;
import org.sc2002.boundary.StudentMainUI;
import org.sc2002.controller.*;
import org.sc2002.entity.*;
import org.sc2002.repository.*;

public class Main {
    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository();
        StaffRepository staffRepository = new StaffRepository();
        CampRepository campRepository = new CampRepository();
        CampStudentRepository campStudentRepository = new CampStudentRepository(campRepository, studentRepository);
        EnquiryRepository enquiryRepository = new EnquiryRepository(campRepository, studentRepository);
        SuggestionRepository suggestionRepository = new SuggestionRepository(campRepository, studentRepository);

        campRepository.load();
        studentRepository.load();
        staffRepository.load();
        campStudentRepository.load();
        enquiryRepository.load();
        suggestionRepository.load();

        CampController campController = new CampController(campRepository);
        UserController userController = new UserController(studentRepository, staffRepository);
        StaffController staffController = new StaffController(campController, staffRepository);
        StudentController studentController = new StudentController(campController, campStudentRepository, studentRepository);
        EnquiryController enquiryController = new EnquiryController(enquiryRepository, campRepository);
        SuggestionController suggestionController = new SuggestionController(suggestionRepository, campRepository);

        LoginUI loginUI = new LoginUI(userController);

        User user = loginUI.body();
        if (userController.getUserRole(user).equals("Staff Member")) {
            Staff staff = (Staff) user;
            StaffUI staffUI = new StaffUI(staff, staffController, studentController , userController, campController, enquiryController, suggestionController);
            staffUI.body();
        } else if (userController.getUserRole(user).equals("Student")) {
            Student student = (Student) user;
            StudentMainUI studentMainUI = new StudentMainUI(student, studentController, userController, campController, enquiryController,suggestionController);
            studentMainUI.body();
        }
    }
}
