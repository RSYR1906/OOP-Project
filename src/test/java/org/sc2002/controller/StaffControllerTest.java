package org.sc2002.controller;

import org.junit.jupiter.api.*;
import org.sc2002.entity.Camp;
import org.sc2002.entity.Entity;
import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.time.LocalDate;

public class StaffControllerTest {

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
    @DisplayName("Test")
    public void StaffController_CreatesCamp_WhenDataIsValid(){

        Staff staff = getAStaff();

        CampController campController = new CampController(campRepository);
        StaffController staffController = new StaffController(campController, campRepository, staffRepository);


        // result
        try{
            staffController.createCamp( "BEACH CAMP", "sports camp to welcome freshmen", LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 13), LocalDate.of(2023, 7, 12), Faculty.ALL, "NTU campus", 100, 30, "HUKUMAR");

            Camp sports_camp = (Camp)campRepository.getByID("BEACH CAMP");
            Assertions.assertEquals("BEACH CAMP", sports_camp.getCampName());
            Assertions.assertEquals(staff.getID(), sports_camp.getStaffInChargeID());
        } catch (Exception e){
            System.out.println("failed");
        }


    }

    @AfterEach
    public void clearUp(){
        try{
            campRepository.remove("BEACH CAMP");
        } catch (Exception e){
            System.out.println("Failed to delete camp");
        }
    }


    public Staff getAStaff() {
        return staffRepository.getAllStaff().get(0);
    }

}
