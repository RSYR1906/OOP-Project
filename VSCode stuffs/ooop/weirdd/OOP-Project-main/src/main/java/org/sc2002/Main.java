package org.sc2002;

import org.sc2002.boundary.LoginUI;
import org.sc2002.boundary.StaffUI;
import org.sc2002.boundary.StudentUI;
import org.sc2002.controller.*;
import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.exception.*;

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
    public static void main(String[] args) throws CampFullException, RegistrationClosedException, EntityNotFoundException, FacultyNotEligibleException, CampConflictException {

        StudentRepository studentRepository = new StudentRepository();
        StaffRepository staffRepository = new StaffRepository();
        CampRepository campRepository = new CampRepository();
        EnqueryRepository enqueryRepository = new EnqueryRepository();
        CampAttendeeRepository campAttendeeRepository = new CampAttendeeRepository();
        CommitteeRepository committeeRepository = new CommitteeRepository();


        campRepository.load();
        studentRepository.load();
        staffRepository.load();
        enqueryRepository.load();
        campAttendeeRepository.load();
        committeeRepository.load();


        CampController campController = new CampController(campRepository, campAttendeeRepository, committeeRepository, studentRepository);
        UserController userController = new UserController(studentRepository, staffRepository);
        CommitteeController committeeController = new CommitteeController(campRepository, campAttendeeRepository, committeeRepository, enqueryRepository, studentRepository, campController);


        EnqueryController enqueryController = new EnqueryController(enqueryRepository, campRepository);
        StudentController studentController = new StudentController(campRepository, enqueryController, studentRepository);
        StaffController staffController = new StaffController(campController, campRepository, staffRepository, enqueryRepository, enqueryController);

        campController.setStudentsCommitteeRegistered();
        enqueryController.setEnqueryCamp();
        LoginUI loginUI = new LoginUI(userController);
        System.out.println("Hello world!");


        User user = loginUI.body();
        if (userController.getUserRole(user).
                equals("Staff Member")) {
            StaffUI staffUI = new StaffUI(staffController, campController, userController, committeeController, campRepository, studentController,user);
            staffUI.body();
        } else if (userController.getUserRole(user).
                equals("Student")) {
            Student student = (Student) user;
            StudentUI studentUI = new StudentUI(studentController, enqueryController, campController, committeeController, student);
            studentUI.body();
        }


    }
}
