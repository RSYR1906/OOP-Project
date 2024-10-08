package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Faculty;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class CampController {

    private CampRepository campRepository;

    public CampController(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    public Camp createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, boolean visibilityToStudent) throws DuplicateEntityExistsException{

        Camp camp = new Camp( campName,  description,  campStartDate,  campEndDate,  campRegistrationEndDate,  userGroupOpenTo,  location, totalSlots,  campCommitteeSlots, staffInChargeID, visibilityToStudent);

        try{
            campRepository.add(camp);
        } catch (DuplicateEntityExistsException e){
            System.out.println("Failed to add entity: " + e.getMessage());
            throw e;
        }
        return camp;
    }

    public Camp editCamp(Camp camp) throws EntityNotFoundException{
        try{
            campRepository.update(camp);
            return camp;
        } catch (EntityNotFoundException e){
            System.out.println("Failed to update entity: " + e.getMessage());
            throw e;
        }
    }

    public void deleteCamp(String campId){
        try{
            campRepository.remove(campId);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
    }

    public Camp getCamp(String campId) throws EntityNotFoundException{
        return campRepository.getCampByID(campId);
    }

    public List<Camp> getAllCamps(){
        return campRepository.getAllCamps();
    }
}
