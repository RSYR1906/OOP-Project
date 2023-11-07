package org.sc2002.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Faculty;
import org.sc2002.entity.Staff;
import org.sc2002.utils.exception.WrongStaffException;

public class StaffController {
    // List to store all camps
    private List<Camp> allCamps;

    // Map to store camps created by each staff member
    private Map<Staff, List<Camp>> staffCamps;

    // Method to create a new camp
    public Camp createCamp(Staff staff, Camp newCamp) {
        // Add the new camp to the list of all camps
        allCamps.add(newCamp);

        // Add the new camp to the list of camps created by the staff
        staffCamps.get(staff).add(newCamp);

        return newCamp;
    }

    // Method to edit an existing camp
    public Camp editCamp(Staff staff, Camp campToEdit, String newDescription, LocalDate newStartDate, LocalDate newEndDate, LocalDate newRegistrationEndDate, Faculty newUserGroup, String newLocation, int newTotalSlots, int newCampCommitteeSlots) throws WrongStaffException {
        // Check if the staff is the owner of the camp
        if (staffCamps.get(staff).contains(campToEdit)) {
            // Update the camp's details
            campToEdit.setDescription(newDescription);
            campToEdit.setCampStartDate(newStartDate);
            campToEdit.setCampEndDate(newEndDate);
            campToEdit.setCampRegistrationEndDate(newRegistrationEndDate);
            campToEdit.setUserGroupOpenTo(newUserGroup);
            campToEdit.setLocation(newLocation);
            campToEdit.setTotalSlots(newTotalSlots);
            campToEdit.setCampCommitteeSlots(newCampCommitteeSlots);

            return campToEdit;
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
            // Handle the error or provide appropriate feedback
            //return null;
        }
    }

    // Method to delete a camp
    public boolean deleteCamp(Staff staff, Camp campToDelete) throws WrongStaffException {
        if (staffCamps.get(staff).contains(campToDelete)) {
            // Remove the camp from the list of all camps
            allCamps.remove(campToDelete);

            // Remove the camp from the staff's created camps
            staffCamps.get(staff).remove(campToDelete);

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

    // Method to view all camps
    public List<Camp> viewAllCamps() {
        return allCamps;
    }

    // Method to view all camps created by the staff
    public List<Camp> viewAllCampsICreated(Staff staff) {
        return staffCamps.get(staff);
    }
}
