package org.sc2002.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;


import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;

public class StaffController {

    private CampController campController;
    private StaffRepository staffRepository;

    public StaffController(CampController campController, StaffRepository staffRepository) {
        this.campController = campController;
        this.staffRepository = staffRepository;
    }

    public Camp createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, Staff staff, boolean visibilityToStudent) throws DuplicateEntityExistsException, EntityNotFoundException{
        Camp camp = campController.createCamp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staff.getID(), visibilityToStudent);
        staff.addToCreatedCamps(camp);
        staffRepository.update(staff);
        return camp;

    }

    public Camp editCamp(Camp camp) throws EntityNotFoundException{
        Camp editedCamp = campController.editCamp(camp);
        return editedCamp;
    }

    public void deleteCamp(Camp camp, Staff staff) throws EntityNotFoundException{
        campController.deleteCamp(camp.getID());
        staff.deleteFromCreatedCamps(camp);
        staffRepository.update(staff);
    }




        //Method to toggle the visibility of a camp with exception handling
    public void toggleVisibilityOfCamp(Camp camp, Staff staff) throws WrongStaffException, EntityNotFoundException {
        // Check if the camp exists
        Camp findCamp = campController.getCamp(camp.getID());
        if(findCamp.getStaffInChargeID().equals(staff.getID())){
            findCamp.setVisibilityToStudent(!findCamp.getVisibilityToStudent());
            campController.editCamp(findCamp);
        } else {
            throw new WrongStaffException();
        }
    }

    public List<Camp> getAllCampsICreated(String staffID) {
        return campController.getAllCamps().stream().filter(camp -> camp.getStaffInChargeID().equals(staffID)).collect(Collectors.toList());
    }

    public void changePassword(Staff staff, String newPassword){
        try{
            staff.setPassword(newPassword);
            staffRepository.update(staff);
            System.out.println("Successfully updated password");
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update password");
        }
    }
}
