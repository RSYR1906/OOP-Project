package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Student;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.CampFullException;
import org.sc2002.utils.exception.FacultyNotEligibleException;
import org.sc2002.utils.exception.RegistrationClosedException;

public class StudentController {

    public void withdrawFromCamp(Student student, Camp camp) throws EntityNotFoundException {
        if (student.getRegisteredCamps().contains(camp)) {
            camp.withdrawStudent(student);
            student.withdrawFromCamp(camp);
        } else {
            throw new EntityNotFoundException("Student is not registered for the camp.");
        }
    }
    
    public void registerCampAsStudent(Student student, Camp camp) throws FacultyNotEligibleException, CampFullException, RegistrationClosedException {
        if (camp.canStudentRegister(student)) {
            camp.registerStudent(student);
            student.registerForCamp(camp);
        } else {
            if (student.getFaculty() != camp.getUserGroupOpenTo()) {
                throw new FacultyNotEligibleException("Student's faculty is not eligible for this camp.");
            } else if (camp.getStudentsRegistered().size() >= camp.getTotalSlots()) {
                throw new CampFullException("The camp is full and cannot accept more registrations.");
            } else {
                throw new RegistrationClosedException("Registration is closed for this camp.");
            }
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

    // Method to handle student leaving the camp committee
    public void leaveCampCommittee(Student student, Camp camp) {
        if (student.isCampCommitteeMember()) {
            student.setCampCommitteeMember(false);
            //need to add the logic to actually remove the student from the committee list in Camp class
        } else {
            // Handle the case where the student is not a committee member
        }
    }

    */

}


