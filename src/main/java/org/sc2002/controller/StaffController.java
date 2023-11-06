package org.sc2002.controller;
import java.util.List;
import java.util.Map;

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
    public Camp editCamp(Staff staff, Camp campToEdit) {
        // Check if the staff is the owner of the camp
        if (staffCamps.get(staff).contains(campToEdit)) {
            // Update the camp's details
            // You can implement the editing logic here
            return campToEdit;
        } else {
            // Camp does not belong to the staff
            // Handle the error or provide appropriate feedback
            return null;
        }
    }

    // Method to delete a camp
    public boolean deleteCamp(Staff staff, Camp campToDelete) {
        if (staffCamps.get(staff).contains(campToDelete)) {
            // Remove the camp from the list of all camps
            allCamps.remove(campToDelete);

            // Remove the camp from the staff's created camps
            staffCamps.get(staff).remove(campToDelete);

            return true;
        } else {
            // Camp does not belong to the staff
            return false;
        }
    }

    // Method to toggle the visibility of a camp
    public void toggleVisibilityOfCamp(Camp camp) {
        // Implement the logic to toggle camp visibility (on/off)
    }

    // Method to view all camps
    public List<Camp> viewAllCamps() {
        return allCamps;
    }

    // Method to view all camps created by the staff
    public List<Camp> viewAllCampsICreated(Staff staff) {
        return staffCamps.get(staff);
    }
