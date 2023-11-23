package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Faculty;
import org.sc2002.entity.Student;
import org.sc2002.repository.CampRepository;
import org.sc2002.utils.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentController {

    private CampRepository campRepository;

    public StudentController(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    public Camp getCamp(String campId) throws EntityNotFoundException {
        return (Camp)campRepository.getByID(campId);
    }

    public void withdrawFromCamp(Student student, Camp camp) throws EntityNotFoundException, Exception {
        if (student.isCampCommitteeMember() && student.getCommitteeMemberCamp().equals(camp)){
            throw new Exception("Committee members not allowed to withdraw from the camp");
        }
        if (student.getRegisteredCamps().contains(camp)) {
            camp.withdrawStudent(student);
            student.withdrawFromCamp(camp);
        } else {
            throw new EntityNotFoundException("Student is not registered for the camp.");
        }
    }
    
    public void registerCampAsStudent(Student student, Camp camp) throws FacultyNotEligibleException, CampFullException, RegistrationClosedException, BlacklistedStudentException, RegisteredAlreadyException {
        if (student.getRegisteredCamps().contains(camp)){
            throw new RegisteredAlreadyException();
        }

        if (camp.canStudentRegister(student)) {
            camp.registerStudent(student);
            student.registerForCamp(camp);
        }
    }

    public void registerCampAsCampCommitteeMember(Student student, Camp camp) throws FacultyNotEligibleException, CampFullException, RegistrationClosedException, BlacklistedStudentException, RegisteredAlreadyException, AlreadyCampCommitteeMemberException{
        if (student.getRegisteredCamps().contains(camp)){
            throw new RegisteredAlreadyException();
        }

        if (student.isCampCommitteeMember()){
            throw new AlreadyCampCommitteeMemberException();
        }

        if(camp.canCampCommitteeMemberRegister(student)){
            camp.registerCampCommitteeMember(student);
            student.registerForCampAsCampCommitteeMember(camp);
        }
    }
    
   public void printRegisteredCamps(Student student){
       ArrayList<Camp> registeredCamps = student.getRegisteredCamps();
       registeredCamps.stream().forEach(camp -> {
           String role = "STUDENT";
           if(student.isCampCommitteeMember() && student.getCommitteeMemberCamp() == camp){
               role = "COMMITTEE";
           }
           System.out.println("CAMP: " + camp.getCampName() + "\t ROLE: " + role);
       });
   }

    public List<Camp> ViewCamp(Student student) {
        List<Camp> camps = campRepository.getAllCamps();

        List<Camp> filteredCamps = camps.stream()
                .filter(camp -> (camp.getUserGroupOpenTo().equals(student.getFaculty()) || camp.getUserGroupOpenTo().equals(Faculty.ALL)))
                .collect(Collectors.toList());

        return filteredCamps;
    }

    public Map<String, Integer> getCampRemainSlots(Student student) {
        List<Camp> camps = ViewCamp(student);

        Map<String, Integer> CampRemainSlots = new HashMap<>();
        for (Camp camp : camps) {
            String name = camp.getCampName();
            int remainSlots = camp.getTotalSlots() - camp.getStudentsRegistered().size() - camp.getCommitteeRegistered().size();
            CampRemainSlots.put(name, remainSlots);
        }
        return CampRemainSlots;
    }

}


