package org.sc2002;

import org.sc2002.boundary.LoginUI;
import org.sc2002.controller.CampController;
import org.sc2002.controller.StaffController;
import org.sc2002.controller.UserController;
import org.sc2002.entity.*;
import org.sc2002.repository.CampRepository;
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

        campRepository.load();
        studentRepository.load();
        staffRepository.load();


        CampController campController = new CampController(campRepository);
        UserController userController = new UserController(studentRepository, staffRepository);
        StaffController staffController = new StaffController(campController);

        LoginUI loginUI = new LoginUI(userController);


        System.out.println("Hello world!");



        loginUI.showMainMenu();


//      // Test StudentRepository
//        Path path = Paths.get(studentRepository.getFilePath());
//        if (!Files.exists(path)) {
//            try {
//                System.out.println("No file at path. Creating new file.");
//                Files.createFile(path);
//            } catch (IOException e) {
//                System.out.println("Failed to create file: " + e.getMessage());
//                return;
//            }
//        }
//
//        studentRepository.load();
//        List<Student> students = studentRepository.getAllStudents();
//        students.forEach(student -> {
//            System.out.println("Name " + student.getName());
//            System.out.println("Email " + student.getEmail());
//            System.out.println("Password " + student.getPassword());
//            System.out.println("Faculty " + student.getFaculty());
//        });
//        Student s1 = new Student("Dave", "DAVE@ntu.edu.sg", "password", "NBS");
//        try{
//            studentRepository.add(s1);
//        } catch (DuplicateEntityExistsException e){
//            System.out.println("Failed to add entity: " + e.getMessage());
//            return;
//        }

        //Test CampController and CampRepository
//        Path campPath = Paths.get(campRepository.getFilePath());
//        if (!Files.exists(campPath)) {
//            try {
//                System.out.println("No file at path. Creating new file.");
//                Files.createFile(campPath);
//            } catch (IOException e) {
//                System.out.println("Failed to create file: " + e.getMessage());
//                return;
//            }
//        }
//
//        campRepository.load();
//        List<Camp> camps = campRepository.getAllCamps();
//        camps.forEach(camp -> {
//            System.out.println("CampName " + camp.getCampName());
//            System.out.println("Description " + camp.getDescription());
//        });
//
//        campController.createCamp("CAC FOP", "CAC Yearly event to welcome freshmen", LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 13), LocalDate.of(2023, 7, 12), Faculty.ALL, "NTU campus", 300, 30);





    }
}
