package org.sc2002.controller;

import org.junit.jupiter.api.*;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.CampStudentRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.*;


public class StudentControllerTest {

    StudentRepository studentRepository;
    StaffRepository staffRepository;
    CampRepository campRepository;
    CampController campController;

    CampStudentRepository campStudentRepository;
    
    StudentController studentController;

    final String TEST_ALL_CAMP = "TEST ALL CAMP";
    final String TEST_SCSE_CAMP = "TEST SCSE CAMP";
    
    final String SCSE_STUDENT_ID = "LE51";
    final String ADM_STUDENT_ID = "DON84";
    final String EEE_STUDENT_ID = "ELI34";
    final String NBS_STUDENT_ID = "SL22";




    @BeforeEach
    public void initialize(){
        this.studentRepository = new StudentRepository();
        this.staffRepository = new StaffRepository();
        this.campRepository = new CampRepository();
        this.campController = new CampController(campRepository);
        this.campStudentRepository = new CampStudentRepository(campRepository, studentRepository);

        campRepository.load();
        studentRepository.load();
        staffRepository.load();
        campStudentRepository.load();

        this.studentController = new StudentController(campController,campStudentRepository, studentRepository);
    }

    @AfterEach
    public void clearUp(){
        campStudentRepository.clear();
    }

    // MethodName_ExpectedBehavior_WhenCondition


    @Test
    @DisplayName("RegistersCamp_WhenDataIsValid")
    public void StudentController_RegistersCamp_WhenDataIsValid(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);

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


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_ALL_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);
            Student testStudent2 = studentRepository.getStudentByID(ADM_STUDENT_ID);

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


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(ADM_STUDENT_ID);

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


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            studentController.registerCampAsStudent(testStudent, testCamp);

            Assertions.assertThrows(RegisteredAlreadyException.class, ()->{
                studentController.registerCampAsStudent(testStudent, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampFails_WhenStudentOnBlacklist")
    public void StudentController_RegistersCampFails_WhenStudentOnBlacklist(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            studentController.registerCampAsStudent(testStudent, testCamp);
            studentController.withdrawFromCamp(testStudent, testCamp);

            Assertions.assertThrows(BlacklistedStudentException.class, ()->{
                studentController.registerCampAsStudent(testStudent, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampFails_WhenNoMoreSlot")
    public void StudentController_RegistersCampFails_WhenNoMoreSlot(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_ALL_CAMP);
            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);
            Student testStudentADM = studentRepository.getStudentByID(ADM_STUDENT_ID);
            Student testStudentEEE = studentRepository.getStudentByID(EEE_STUDENT_ID);

            studentController.registerCampAsStudent(testStudentSCSE, testCamp);
            studentController.registerCampAsStudent(testStudentADM, testCamp);

            Assertions.assertThrows(CampFullException.class, ()->{
                studentController.registerCampAsStudent(testStudentEEE, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("WithdrawCamp_WhenDataIsValid")
    public void StudentController_WithdrawCamp_WhenDataIsValid(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            studentController.registerCampAsStudent(testStudent, testCamp);
            studentController.withdrawFromCamp(testStudent, testCamp);

            Assertions.assertFalse(testStudent.getRegisteredCamps().contains(testCamp));
            Assertions.assertFalse(testCamp.getStudentsRegistered().contains(testStudent));
            Assertions.assertTrue(testCamp.getStudentBlacklist().contains(testStudent));
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("WithdrawCamp_WhenNotRegistered")
    public void StudentController_WithdrawCampFails_WhenNotRegistered(){

        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            Assertions.assertFalse(testStudent.getRegisteredCamps().contains(testCamp));
            Assertions.assertFalse(testCamp.getStudentsRegistered().contains(testStudent));
            Assertions.assertThrows(EntityNotFoundException.class, ()->{
                studentController.withdrawFromCamp(testStudent, testCamp);
            });
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    // Camp committee member registration
    @Test
    @DisplayName("RegistersCampAsCommitteeMember_WhenDataIsValid")
    public void StudentController_RegistersCampAsCommitteeMember_WhenDataIsValid(){

        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudent = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            studentController.registerCampAsCampCommitteeMember(testStudent, testCamp);

            Assertions.assertTrue(testStudent.getRegisteredCamps().contains(testCamp));
            Assertions.assertTrue(testCamp.getCommitteeRegistered().contains(testStudent));
            Assertions.assertFalse(testCamp.getStudentsRegistered().contains(testStudent));
            Assertions.assertTrue(testStudent.isCampCommitteeMember());
            Assertions.assertEquals(testCamp,testStudent.getCommitteeMemberCamp());
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampAsCommitteeMemberFails_WhenNoMoreSlot")
    public void StudentController_RegistersCampAsCommitteeMemberFails_WhenNoMoreSlot(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_ALL_CAMP);
            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);
            Student testStudentADM = studentRepository.getStudentByID(ADM_STUDENT_ID);


            studentController.registerCampAsCampCommitteeMember(testStudentSCSE, testCamp);

            Assertions.assertThrows(CampFullException.class, ()->{
                studentController.registerCampAsCampCommitteeMember(testStudentADM, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampAsCommitteeMemberFails_WhenAlreadyRegisteredAsStudent")
    public void StudentController_RegistersCampAsCommitteeMemberFails_WhenAlreadyRegisteredAsStudent(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_ALL_CAMP);
            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);


            studentController.registerCampAsStudent(testStudentSCSE, testCamp);

            Assertions.assertThrows(RegisteredAlreadyException.class, ()->{
                studentController.registerCampAsCampCommitteeMember(testStudentSCSE, testCamp);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    @Test
    @DisplayName("RegistersCampAsCommitteeMemberFails_WhenAlreadyCampCommitteeMember")
    public void StudentController_RegistersCampAsCommitteeMemberFails_WhenAlreadyCampCommitteeMember(){

        

        // result
        try{
            Camp testCampALL = campRepository.getCampByID(TEST_ALL_CAMP);
            Camp testCampSCSE = campRepository.getCampByID(TEST_SCSE_CAMP);

            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);


            studentController.registerCampAsCampCommitteeMember(testStudentSCSE, testCampALL);

            Assertions.assertThrows(AlreadyCampCommitteeMemberException.class, ()->{
                studentController.registerCampAsCampCommitteeMember(testStudentSCSE, testCampSCSE);
            });

        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    // Student registering for multiple camps
    @Test
    @DisplayName("RegistersMultipleCampsAsStudent_WhenDataIsValid")
    public void StudentController_RegistersMultipleCampsAsStudent_WhenDataIsValid(){


        

        // result
        try{
            Camp testCampAll = (Camp)campRepository.getCampByID(TEST_ALL_CAMP);
            Camp testCampSCSE = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            studentController.registerCampAsStudent(testStudentSCSE, testCampAll);
            studentController.registerCampAsStudent(testStudentSCSE, testCampSCSE);



            Assertions.assertTrue(testStudentSCSE.getRegisteredCamps().contains(testCampAll));
            Assertions.assertTrue(testStudentSCSE.getRegisteredCamps().contains(testCampSCSE));
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }

    // Test printing out camps registered
    @Test
    @DisplayName("PrintRegisteredCampsOfStudent")
    public void StudentController_PrintRegisteredCampsOfStudent(){


        

        // result
        try{
            Camp testCampAll = campRepository.getCampByID(TEST_ALL_CAMP);
            Camp testCampSCSE = campRepository.getCampByID(TEST_SCSE_CAMP);
            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);

            studentController.registerCampAsStudent(testStudentSCSE, testCampAll);
            studentController.registerCampAsCampCommitteeMember(testStudentSCSE, testCampSCSE);


            studentController.printRegisteredCamps(testStudentSCSE);
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }



    // Complex test involving camp committee member and student withdrawal
    @Test
    @DisplayName("RegistersCamp_WhenDataIsValid")
    public void StudentController_RegistersCampForStudentAndCommittee_WhenDataIsValid(){


        

        // result
        try{
            Camp testCamp = campRepository.getCampByID(TEST_ALL_CAMP);
            Student testStudentSCSE = studentRepository.getStudentByID(SCSE_STUDENT_ID);
            Student testStudentADM = studentRepository.getStudentByID(ADM_STUDENT_ID);
            Student testStudentEEE = studentRepository.getStudentByID(EEE_STUDENT_ID);
            Student testStudentNBS = studentRepository.getStudentByID(NBS_STUDENT_ID);

            studentController.registerCampAsStudent(testStudentSCSE, testCamp);
            studentController.registerCampAsStudent(testStudentADM, testCamp);
            studentController.withdrawFromCamp(testStudentSCSE,testCamp);
            studentController.registerCampAsStudent(testStudentEEE, testCamp);
            studentController.registerCampAsCampCommitteeMember(testStudentNBS, testCamp);


            Assertions.assertTrue(testStudentADM.getRegisteredCamps().contains(testCamp));
            Assertions.assertTrue(testCamp.getStudentsRegistered().contains(testStudentADM));

            Assertions.assertFalse(testCamp.getStudentsRegistered().contains(testStudentSCSE));
            Assertions.assertTrue(testCamp.getCommitteeRegistered().contains(testStudentNBS));
        } catch (Exception e){
            System.out.println("failed: " + e.getMessage());
            Assertions.fail("Failed test");
        }
    }




}
