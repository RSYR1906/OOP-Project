package org.sc2002.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;


import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StaffController {

    private CampController campController;
    private CampRepository campRepository;
    private StaffRepository staffRepository;

    public StaffController(CampController campController, CampRepository campRepository, StaffRepository staffRepository) {
        this.campController = campController;
        this.campRepository = campRepository;
        this.staffRepository = staffRepository;
    }
    
    public Camp createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, Boolean visibilityToStudent) throws DuplicateEntityExistsException, EntityNotFoundException{
        Camp createdCamp = campController.createCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInChargeID, visibilityToStudent);
        staffRepository.getStaffByID(staffInChargeID).addToCreatedCamps(createdCamp);
        return createdCamp;
    }

    // Method to edit an existing camp
    public Camp editCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID,  boolean visibilityToStudent) throws WrongStaffException, EntityNotFoundException {
        // Check if the staff is the owner of the camp
        if (campRepository.getCampByID(campName).getStaffInChargeID().equals(staffInChargeID)) {
            Camp editedCamp = campController.editCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInChargeID,  visibilityToStudent);
            staffRepository.getStaffByID(staffInChargeID).deleteFromCreatedCamps(editedCamp);
            staffRepository.getStaffByID(staffInChargeID).addToCreatedCamps(editedCamp);
            return editedCamp;
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
            // Handle the error or provide appropriate feedback
            //return null;
        }
    }

    public void deleteCamp(String campID, String staffInChargeID) throws WrongStaffException, EntityNotFoundException {
        if (campRepository.getCampByID(campID).getStaffInChargeID().equals(staffInChargeID)) {
            Camp campToDelete = campRepository.getCampByID(campID);
            campController.deleteCamp(campID);
            // Remove the camp from the staff's created camps
            staffRepository.getStaffByID(staffInChargeID).deleteFromCreatedCamps(campToDelete);

            // Remove the camp from the list of all camps
            campController.deleteCamp(campToDelete.getID());

            return;
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
        }
    }

     //Method to toggle the visibility of a camp with exception handling
    public void toggleVisibilityOfCamp(Camp camp, Staff staff) throws WrongStaffException, EntityNotFoundException {
        // Check if the camp exists
        Camp findCamp = campRepository.getCampByID(camp.getID());
        if(findCamp.getStaffInChargeID().equals(staff.getID())){
            findCamp.setVisibilityToStudent(!findCamp.getVisibilityToStudent());
            campRepository.update(findCamp);
        } else {
            throw new WrongStaffException();
        }
    }


    // Method to view all camps
    public List<Camp> viewAllCamps() {
        return campRepository.getAllCamps();
    }

    // Method to view all camps created by the staff
    public List<Camp> viewAllCampsICreated(String staffID) throws EntityNotFoundException {
        List<Entity> entities = campRepository.getAll();
        List<Camp> camps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Camp && campRepository.getCampByID(entity.getID()).getStaffInChargeID().equals(staffID)) {
                camps.add((Camp) entity);
            }
        }
        return camps;
    }
       public void createReportForStaff(Staff staff, String reportType) throws EntityNotFoundException, IOException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        List<Camp> newCamp = new ArrayList<>();

        for (Camp camp : camps) {
            Camp temp = new Camp();
            ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
            String campName = camp.getCampName();
            ArrayList<String> committeeRegistered = camp.getCommitteeRegistered();
            String staffInChargeID = camp.getStaffInChargeID();
            temp.setStudentsRegistered(studentsRegistered);
            temp.setCampName(campName);
            temp.setCommitteeRegistered(committeeRegistered);
            temp.setStaffInChargeID(staffInChargeID);
            newCamp.add(temp);
        }

        if ("csv".equalsIgnoreCase(reportType)) {
            generateCSVReport(newCamp, "campReport.csv");
        } else if ("txt".equalsIgnoreCase(reportType)) {
            generateTXTReport(newCamp, "campReport.txt");
        }
    }

    private void generateCSVReport(List<Camp> camps, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Camp Name,Staff In Charge ID,Students Registered,Committee Registered\n");

            for (Camp camp : camps) {
                String campInfo = String.join(",",
                        camp.getCampName(),
                        camp.getStaffInChargeID(),
                        String.join(" ", camp.getStudentsRegistered().stream().map(Student::getName).collect(Collectors.toList())),
                        String.join(" ", camp.getCommitteeRegistered()));
                writer.append(campInfo).append("\n");
            }
        }
    }

    private void generateTXTReport(List<Camp> camps, String fileName) throws IOException {
        Path file = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Camp camp : camps) {
                writer.write("Camp Name: " + camp.getCampName() + "\n");
                writer.write("Staff In Charge ID: " + camp.getStaffInChargeID() + "\n");
                writer.write("Students Registered: " + String.join(", ", camp.getStudentsRegistered().stream().map(Student::getName).collect(Collectors.toList())) + "\n");
                writer.write("Committee Registered: " + String.join(", ", camp.getCommitteeRegistered()) + "\n");
                writer.write("------------------------------------------------------------\n");
            }
        }
    }
}

