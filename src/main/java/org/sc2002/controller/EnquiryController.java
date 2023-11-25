package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Enquiry;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.EnquiryRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class EnquiryController {

    private EnquiryRepository enquiryRepository;

    private CampRepository campRepository;

    public EnquiryController(EnquiryRepository enquiryRepository, CampRepository campRepository) {
        this.enquiryRepository = enquiryRepository;
        this.campRepository = campRepository;
    }

    public boolean addEnquiry(Enquiry enquiry){
        try{
            enquiryRepository.add(enquiry);
            return true;
        } catch (DuplicateEntityExistsException e){
            System.out.println("Failed to add entity: " + e.getMessage());
        }
        return false;
    }

    public void editEnquiry(Enquiry enquiry) {
        try{
            enquiryRepository.update(enquiry);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to edit entity: " + e.getMessage());
        }
    }

    public void deleteEnquiry(String enquiryID){
        try{
            enquiryRepository.remove(enquiryID);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
    }

    public List<Enquiry> getEnquiryByStaff(Staff staff){
        return enquiryRepository.getAllEnquiry().stream().filter(enquiry ->
            enquiry.getStaffID().equals(staff.getID())).collect(Collectors.toList());
    }

    public List<Enquiry> getEnquiryByCamp(Camp camp){
        return enquiryRepository.getAllEnquiry().stream().filter(enquiry ->
                enquiry.getCamp().equals(camp)).collect(Collectors.toList());
    }

    public List<Enquiry> getEnquiryByStudent(Student student){
        return enquiryRepository.getAllEnquiry().stream().filter(enquiry ->
                enquiry.getStudent().equals(student)).collect(Collectors.toList());
    }
}
