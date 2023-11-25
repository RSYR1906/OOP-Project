package org.sc2002.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sc2002.entity.Student;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.EnqueryRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.EntityNotFoundException;

public class CommitteeControllerTest {

    StudentRepository studentRepository;
    StaffRepository staffRepository;
    CampRepository campRepository;
    EnqueryRepository enqueryRepository;


    @BeforeEach
    public void initialize(){
        this.studentRepository = new StudentRepository();
        this.staffRepository = new StaffRepository();
        this.campRepository = new CampRepository();
        this.enqueryRepository = new EnqueryRepository();

        campRepository.load();
        studentRepository.load();
        staffRepository.load();
        enqueryRepository.load();
    }


    private CommitteeController committeeController=new CommitteeController(campRepository,enqueryRepository,studentRepository);

//    @Test
//    @DisplayName("test")
//    public void viewAllSuggestionsTest(){
//        String name="Sam";
//        String email="sam@ntu.edu.sg";
//        String password="password";
//        String faculty="NBS";
//        Student student=new Student(name,email,password,faculty);
//
//        try {
//            committeeController.viewAllSuggestions(student);
//        } catch (EntityNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }

}
