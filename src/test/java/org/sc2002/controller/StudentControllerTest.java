package org.sc2002.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.FacultyNotEligibleException;
import org.sc2002.utils.exception.RegisteredAlreadyException;


public class StudentControllerTest {

    StudentRepository studentRepository;
    StaffRepository staffRepository;
    CampRepository campRepository;


    @BeforeEach
    public void initialize(){
        this.studentRepository = new StudentRepository();
        this.staffRepository = new StaffRepository();
        this.campRepository = new CampRepository();

        campRepository.load();
        studentRepository.load();
        staffRepository.load();
    }

    // MethodName_ExpectedBehavior_WhenCondition


    @Test
    @DisplayName("RegistersCamp_WhenDataIsValid")
    public void StudentController_RegistersCamp_WhenDataIsValid(){


        StudentController studentController = new StudentController();

        // result
        try{
            Camp testCamp = (Camp)campRepository.getByID("SCSE FOP");
            Student testStudent = studentRepository.getStudentByID("LE51");

            studentController.registerCampAsStudent(testStudent, testCamp);

            Assertions.assertTrue(testStudent.getRegisteredCamps().contains(testCamp));
            Assertions.assertTrue(testCamp.getStudentsRegistered().contains(testStudent));
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCamp_WhenCampIsOpenToAll")
    public void StudentController_RegistersCamp_WhenCampIsOpenToAll(){


        StudentController studentController = new StudentController();

        // result
        try{
            Camp testCamp = (Camp)campRepository.getByID("CAC FOP");
            Student testStudent = studentRepository.getStudentByID("LE51");
            Student testStudent2 = studentRepository.getStudentByID("DENISE");

            studentController.registerCampAsStudent(testStudent, testCamp);
            studentController.registerCampAsStudent(testStudent2, testCamp);

            Assertions.assertTrue(testStudent.getRegisteredCamps().contains(testCamp));
            Assertions.assertTrue(testCamp.getStudentsRegistered().contains(testStudent));

            Assertions.assertTrue(testStudent2.getRegisteredCamps().contains(testCamp));
            Assertions.assertTrue(testCamp.getStudentsRegistered().contains(testStudent2));
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampFails_WhenWrongFaculty")
    public void StudentController_RegistersCampFails_WhenWrongFaculty(){


        StudentController studentController = new StudentController();

        // result
        try{
            Camp testCamp = (Camp)campRepository.getByID("SCSE FOP");
            Student testStudent = studentRepository.getStudentByID("DENISE");

            Assertions.assertThrows(FacultyNotEligibleException.class, ()->{
                studentController.registerCampAsStudent(testStudent, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampFails_WhenRegisterSameCampAgain")
    public void StudentController_RegistersCampFails_WhenRegisterSameCampAgain(){


        StudentController studentController = new StudentController();

        // result
        try{
            Camp testCamp = (Camp)campRepository.getByID("SCSE FOP");
            Student testStudent = studentRepository.getStudentByID("LE51");

            studentController.registerCampAsStudent(testStudent, testCamp);

            Assertions.assertThrows(RegisteredAlreadyException.class, ()->{
                studentController.registerCampAsStudent(testStudent, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

}
