package org.sc2002.controller;

import org.sc2002.entity.*;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.StaffRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Student controller.
 */
public class StudentController {
    private CampRepository campRepository;
    private EnqueryController enqueryController;
    private StudentRepository studentRepository;


    /**
     * Instantiates a new Student controller.
     */
    public StudentController() {

    }

    /**
     * Gets camp.
     *
     * @param campId the camp id
     * @return the camp
     * @throws EntityNotFoundException the entity not found exception
     */
    public Camp getCamp(String campId) throws EntityNotFoundException {
        return (Camp)campRepository.getByID(campId);
    }

    /**
     * Instantiates a new Student controller.
     *
     * @param campRepository    the camp repository
     * @param enqueryController the enquery controller
     */
    public StudentController(CampRepository campRepository, EnqueryController enqueryController, StudentRepository studentRepository) {
        this.campRepository = campRepository;
        this.enqueryController = enqueryController;
        this.studentRepository =  studentRepository;
    }

    /**
     * Withdraw from camp.
     *
     * @param student the student
     * @param camp    the camp
     * @throws EntityNotFoundException the entity not found exception
     */
    public void withdrawFromCamp(Student student, Camp camp) throws EntityNotFoundException {
        if (student.getRegisteredCamps().contains(camp)) {
            camp.withdrawStudent(student);
            student.withdrawFromCamp(camp);
            camp.setStudentsWithdraw(student);
        } else {
            throw new EntityNotFoundException("Student is not registered for the camp.");
        }
    }

    /**
     * Is time conflict boolean.
     *
     * @param camp            the camp
     * @param registeredCamps the registered camps
     * @return the boolean
     */
    public boolean isTimeConflict(Camp camp, List<String> registeredCamps) {
        LocalDate campStartDate = camp.getCampStartDate();
        LocalDate campEndDate = camp.getCampEndDate();

        for (String campIds : registeredCamps) {
            Camp campRegistered = null;
            try {
                campRegistered = campRepository.getCampByID(campIds);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
            assert campRegistered != null;
            LocalDate registeredCampStartDate = campRegistered.getCampStartDate();
            LocalDate registeredCampEndDate = campRegistered.getCampEndDate();

            if (!(campEndDate.isBefore(registeredCampStartDate) || campStartDate.isAfter(registeredCampEndDate))) {

                return true;
            }
        }


        return false;
    }

    /**
     * Is before deadline boolean.
     *
     * @param student                 the student
     * @param campRegistrationEndDate the camp registration end date
     * @return the boolean
     */
    public boolean isBeforeDeadline(Student student, LocalDate campRegistrationEndDate) {
        LocalDate now = LocalDate.now();
        return !now.isAfter(campRegistrationEndDate);
    }

    /**
     * Register camp.
     *
     * @param student the student
     * @param camp    the camp
     * @throws FacultyNotEligibleException the faculty not eligible exception
     * @throws CampFullException           the camp full exception
     * @throws RegistrationClosedException the registration closed exception
     * @throws CampConflictException       the camp conflict exception
     */
    public void registerCamp(Student student, Camp camp) throws FacultyNotEligibleException, CampFullException, RegistrationClosedException, CampConflictException {
        if (student.getCampCommitteeMember().length() != 0) {
            throw new CampConflictException("The student has already registered as a committee");
        }
        if (student.getFaculty() != camp.getUserGroupOpenTo()) {
            throw new FacultyNotEligibleException("Student's faculty is not eligible for this camp.");
        }
        if (camp.getStudentsRegistered().size() >= camp.getTotalSlots()) {
            throw new CampFullException("The camp is full and cannot accept more registrations.");
        }
        if (!isBeforeDeadline(student, camp.getCampRegistrationEndDate())) {
            throw new RegistrationClosedException("Registration is closed for this camp.");
        }
        List<Camp> camps = student.getRegisteredCamps();
        List<String> registeredCamps = new ArrayList<>();
        for (Camp camp1 : camps) {
            registeredCamps.add(camp1.getID());
        }

        if (isTimeConflict(camp, registeredCamps)) {
            throw new CampConflictException("The campsite where students participate has a time conflict");
        }

        if (student.getCampCommitteeMember().length() != 0) {
            throw new CampConflictException("The student has already registered as a committee");
        }
        camp.registerStudent(student);
        student.registerForCamp(camp);
    }

    /**
     * View camp list.
     *
     * @param student the student
     * @return the list
     */
    public List<Camp> ViewCamp(Student student) {
        List<Camp> camps = campRepository.getAllCamps();
        return camps.stream()
                .filter(camp -> (camp.getUserGroupOpenTo().equals(student.getFaculty()) || camp.getUserGroupOpenTo().equals(Faculty.ALL)) && camp.isVisibilityToStu())
                .collect(Collectors.toList());
    }

    /**
     * Gets camp remain slots.
     *
     * @param student the student
     * @return the camp remain slots
     */
    public Map<Camp, Integer> getCampRemainSlots(Student student) {
        List<Camp> camps = ViewCamp(student);
        Map<Camp, Integer> CampRemainSlots = new HashMap<>();
        for (Camp camp : camps) {
            String name = camp.getCampName();
            int remainSlots = camp.getTotalSlots() - camp.getStudentsRegistered().size() - camp.getCommitteeRegistered().size();
            CampRemainSlots.put(camp, remainSlots);
        }
        return CampRemainSlots;
    }

    /**
     * Gets camp name remain slots.
     *
     * @param student the student
     * @return the camp name remain slots
     */
    public Map<String, Integer> getCampNameRemainSlots(Student student) {
        List<Camp> camps = ViewCamp(student);
        Map<String, Integer> CampRemainSlots = new HashMap<>();
        for (Camp camp : camps) {
            String name = camp.getCampName();
            int remainSlots = camp.getTotalSlots() - camp.getStudentsRegistered().size() - camp.getCommitteeRegistered().size();
            CampRemainSlots.put(name, remainSlots);
        }
        return CampRemainSlots;
    }


    /**
     * Register committee.
     *
     * @param student the student
     * @param camp    the camp
     * @throws CampFullException     the camp full exception
     * @throws CampConflictException the camp conflict exception
     */
    public void registerCommittee(Student student, Camp camp) throws CampFullException, CampConflictException {
        if (student.getCampCommitteeMember().length() == 0 && camp.getCommitteeRegistered().size() < camp.getCampCommitteeSlots()) {
            student.setCampCommitteeMember(camp.getCampName());
            camp.registerCommittee(student);
        } else {
            // Handle the case where the student cannot join the committee
            throw new CampConflictException("Cannot join the camp as a member of the committee");
        }
    }

    /**
     * Leave camp committee.
     *
     * @param student the student
     * @param camp    the camp
     * @throws EntityNotFoundException the entity not found exception
     * @throws CampConflictException   the camp conflict exception
     */
// Method to handle student leaving the camp committee
    public void leaveCampCommittee(Student student, Camp camp) throws EntityNotFoundException, CampConflictException {
        if (student.getCampCommitteeMember().length() != 0) {
            student.setCampCommitteeMember(null);
            //need to add the logic to actually remove the student from the committee list in Camp class
            camp.withdrawCommittee(student);
            camp.setStudentsWithdraw(student);
        } else {
            // Handle the case where the student is not a committee member
            throw new CampConflictException("the student is not a committee member");
        }
    }

    /**
     * Add enquiry boolean.
     *
     * @param enquery the enquery
     * @return the boolean
     */
    public boolean addEnquiry(Enquery enquery) {
        if (enqueryController.addEnquery(enquery)) return true;
        return false;
    }

    /**
     * Add enquiry boolean.
     *
     * @param studentId the student id
     * @param query     the query
     * @param campId    the camp id
     * @return the boolean
     */
    public boolean addEnquiry(String studentId, String query, String campId) {
        if (enqueryController.addEnqueryByStudent(studentId, query, campId)) return true;
        return false;
    }

    public void editStudent(Student student){
        studentRepository.saveStudent(student);
    }

    public void studentAddOneScore(Student student){
        student.setScore(student.getScore()+1);
        editStudent(student);
    }

    public void studentAddOneScore(String studentId) {
        Student student = null;
        try {
            student = studentRepository.getStudentByID(studentId);
            student.setScore(student.getScore()+1);
            editStudent(student);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

    }

}


