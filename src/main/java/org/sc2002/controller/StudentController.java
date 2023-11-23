package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.*;

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
    
   /* public void joinCampCommittee(Student student, Camp camp) {
        if (!student.isCampCommitteeMember() && camp.getCommitteeRegistered().length < camp.getCampCommitteeSlots()) {
            student.setCampCommitteeMember(true);
            //  need to add the logic to actually add the student to the committee list in Camp class
        } else {
            // Handle the case where the student cannot join the committee
        }
    }
    */

}


