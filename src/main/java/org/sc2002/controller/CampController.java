package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Faculty;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.time.LocalDate;

public class CampController {

    private CampRepository campRepository;

    public CampController(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    public void createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, Staff staffInCharge){

        Camp camp = new Camp( campName,  description,  campStartDate,  campEndDate,  campRegistrationEndDate,  userGroupOpenTo,  location, totalSlots,  campCommitteeSlots, staffInCharge);

        try{
            campRepository.add(camp);
        } catch (DuplicateEntityExistsException e){
            System.out.println("Failed to add entity: " + e.getMessage());
            return;
        }
    }

    public void editCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, Staff staffInCharge){

        Camp newCamp = new Camp( campName,  description,  campStartDate,  campEndDate,  campRegistrationEndDate,  userGroupOpenTo,  location, totalSlots,  campCommitteeSlots, staffInCharge);

        try{
            campRepository.update(newCamp);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update entity: " + e.getMessage());
            return;
        }
    }

    public void deleteCamp(String campId){
        try{
            campRepository.remove(campId);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
    }
}
