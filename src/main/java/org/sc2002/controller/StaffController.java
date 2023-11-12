package org.sc2002.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.sc2002.entity.*;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;

public class StaffController {
    private CampRepository campRepository;
    private CampController campController;

    public StaffController(CampController campController, CampRepository campRepository){
        this.campRepository = campRepository;
        this.campController = campController;
    }
    
    public void createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, Staff staffInCharge){
        campController.createCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInCharge);
    }

    // Method to edit an existing camp
    public void editCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, Staff staffInCharge) throws WrongStaffException, EntityNotFoundException {
        // Check if the staff is the owner of the camp
        if (campRepository.getCampByID(campName).getStaffInCharge().getID().equals(staffInCharge.getID())) {
            campController.editCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInCharge);
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
        }
    }

    // Method to delete a camp
    public void deleteCamp(String campID, Staff staffInCharge) throws WrongStaffException, EntityNotFoundException {
        if (campRepository.getCampByID(campID).getStaffInCharge().getID().equals(staffInCharge.getID())) {
            campController.deleteCamp(campID);
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
        }
    }

    // Method to toggle the visibility of a camp with exception handling
//    public void toggleVisibilityOfCamp(Camp camp) throws WrongStaffException {
//        // Check if the camp exists
//        if (allCamps.contains(camp)) {                                        //Need to add an isVisible() to the Camp.java
//            // Check the current visibility status of the camp
//            if (camp.isVisible()) {
//                // If the camp is currently visible, set it to "off"
//                camp.setVisible(false);
//            } else {
//                // If the camp is currently not visible, set it to "on"
//                camp.setVisible(true);
//            }
//        } else {
//            // Camp does not exist, throw a custom exception
//            throw new WrongStaffException("Camp does not exist or is not accessible by the staff.");
//        }
//    }

    // Method to view all camps
    public List<Camp> viewAllCamps() {
        return campRepository.getAllCamps();
    }

    // Method to view all camps created by the staff
    public List<Camp> viewAllCampsICreated(Staff staff) throws EntityNotFoundException {
        List<Entity> entities = campRepository.getAll();
        List<Camp> camps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Camp && campRepository.getCampByID(entity.getID()).getStaffInCharge().getID().equals(staff.getID())) {
                camps.add((Camp) entity);
            }
        }
        return camps;
    }
}
