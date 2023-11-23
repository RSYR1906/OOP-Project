package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.*;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    public void withdrawFromCamp(Student student, Camp camp) throws EntityNotFoundException {
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

}


