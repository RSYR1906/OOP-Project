package org.sc2002.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        StaffController staffController = new StaffController(campController);

        staffController.createCamp(staff, "BEACH CAMP", "sports camp to welcome freshmen", LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 13), LocalDate.of(2023, 7, 12), Faculty.ALL, "NTU campus", 100, 30);

        // result
        try{
            Camp sports_camp = (Camp)campRepository.getByID("BEACH CAMP");
            Assertions.assertEquals("BEACH CAMP", sports_camp.getCampName());
        } catch (EntityNotFoundException e){
            System.out.println("failed");
        }


    }


    public Staff getAStaff() {
        return staffRepository.getAllStaff().get(0);
    }

}
