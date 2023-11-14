package org.sc2002.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;

public class StaffController {

    private CampController campController;

    public StaffController(CampController campController) {
        this.campController = campController;
    }

    // Method to create a new camp
    public void createCamp(Staff staff, String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots) {
        try{
            Camp createdCamp = campController.createCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots);
            staff.addToCreatedCamps(createdCamp);
        } catch (DuplicateEntityExistsException e){
            System.out.println("Staff Failed to add entity: " + e.getMessage());
        }
    }

    // Method to edit an existing camp
    public void editCamp(Staff staff, Camp campToEdit, String newDescription, LocalDate newStartDate, LocalDate newEndDate, LocalDate newRegistrationEndDate, Faculty newUserGroup, String newLocation, int newTotalSlots, int newCampCommitteeSlots) throws WrongStaffException {
        // Check if the staff is the owner of the camp
        if (staff.getCreatedCamps().contains(campToEdit)) {
            try{
                Camp editedCamp = campController.editCamp(campToEdit.getCampName(), newDescription, newStartDate, newEndDate, newRegistrationEndDate, newUserGroup, newLocation, newTotalSlots, newCampCommitteeSlots);
                staff.deleteFromCreatedCamps(campToEdit);
                staff.addToCreatedCamps(editedCamp);
            } catch (EntityNotFoundException e){
                System.out.println("Entity not found!: " + e.getMessage());
            }
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
            // Handle the error or provide appropriate feedback
            //return null;
        }
    }

    // Method to delete a camp
    public boolean deleteCamp(Staff staff, Camp campToDelete) throws WrongStaffException {
        if (staff.getCreatedCamps().contains(campToDelete)) {

            // Remove the camp from the staff's created camps
            staff.deleteFromCreatedCamps(campToDelete);

            // Remove the camp from the list of all camps
            campController.deleteCamp(campToDelete.getID());

            return true;
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
}
