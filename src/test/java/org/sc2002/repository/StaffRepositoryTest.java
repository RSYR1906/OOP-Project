package org.sc2002.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffRepositoryTest {

    @Test
    public void CreateStaffRepository_If_No_FileAtPath(){

        StaffRepository staffRepository = new StaffRepository();

        // Test StaffRepository
        Path path = Paths.get(staffRepository.getFilePath());
        if (!Files.exists(path)) {
            try {
                System.out.println("No file at path. Creating new file.");
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Failed to create file: " + e.getMessage());
            }
        }

    }

    @Test
    public void StaffRepository_Loads_Staff(){
        StaffRepository staffRepository = new StaffRepository();
        staffRepository.load();
        List<Staff> staffs = staffRepository.getAllStaff();
        staffs.forEach(staff -> {
            System.out.println("Name " + staff.getName());
            System.out.println("Email " + staff.getEmail());
            System.out.println("Password " + staff.getPassword());
            System.out.println("Faculty " + staff.getFaculty());
        });
    }

    @Test
    public void StaffRepository_SavesStaff_WhenDataIsValid(){

        StaffRepository staffRepository = new StaffRepository();
        staffRepository.load();
        Staff staff1 = new Staff("Jeff", "JEFF101@ntu.edu.sg", "password", "NBS");
        try{
            staffRepository.add(staff1);
        } catch (DuplicateEntityExistsException e){
            System.out.println("Failed to add entity: " + e.getMessage());
            return;
        }

        try{
            Staff foundStaff = staffRepository.getStaffByID("JEFF101");
            assertEquals("Jeff", foundStaff.getName());
            assertEquals(Faculty.NBS, foundStaff.getFaculty());
        } catch (EntityNotFoundException e){
            System.out.println("Failed to find entity: " + e.getMessage());
        }

    }
}
