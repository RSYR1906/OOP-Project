package org.sc2002.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;

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
}
