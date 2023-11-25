package org.sc2002.controller;

import org.sc2002.entity.*;
import org.sc2002.repository.CampAttendeeRepository;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.CommitteeRepository;
import org.sc2002.repository.StudentRepository;
import org.sc2002.utils.exception.CampConflictException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Camp controller.
 */
public class CampController {

    private CampRepository campRepository;
    private CampAttendeeRepository campAttendeeRepository;
    private CommitteeRepository committeeRepository;
    private StudentRepository studentRepository;
    private EnqueryController enqueryController;
    /**
     * Instantiates a new Camp controller.
     *
     * @param campRepository the camp repository
     */
    public CampController(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    /**
     * Instantiates a new Camp controller.
     *
     * @param campRepository         the camp repository
     * @param campAttendeeRepository the camp attendee repository
     */
    public CampController(CampRepository campRepository, CampAttendeeRepository campAttendeeRepository) {
        this.campRepository = campRepository;
        this.campAttendeeRepository = campAttendeeRepository;
    }

    /**
     * Instantiates a new Camp controller.
     *
     * @param campRepository         the camp repository
     * @param campAttendeeRepository the camp attendee repository
     * @param committeeRepository    the committee repository
     * @param studentRepository      the student repository
     */
    public CampController(CampRepository campRepository, CampAttendeeRepository campAttendeeRepository, CommitteeRepository committeeRepository, StudentRepository studentRepository) {
        this.campRepository = campRepository;
        this.campAttendeeRepository = campAttendeeRepository;
        this.committeeRepository = committeeRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Gets camp by id.
     *
     * @param campId the camp id
     * @return the camp by id
     */
    public Camp getCampById(String campId) {
        try {
            return campRepository.getCampByID(campId);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create camp camp.
     *
     * @param camp the camp
     * @return the camp
     */
    public Camp createCamp(Camp camp) {
        try {
            campRepository.add(camp);
        } catch (DuplicateEntityExistsException e) {
            System.out.println("Failed to add entity: " + e.getMessage());
        }
        return camp;
    }

    /**
     * Create camp camp.
     *
     * @param campName                the camp name
     * @param description             the description
     * @param campStartDate           the camp start date
     * @param campEndDate             the camp end date
     * @param campRegistrationEndDate the camp registration end date
     * @param userGroupOpenTo         the user group open to
     * @param location                the location
     * @param totalSlots              the total slots
     * @param campCommitteeSlots      the camp committee slots
     * @param staffInChargeID         the staff in charge id
     * @return the camp
     * @throws DuplicateEntityExistsException the duplicate entity exists exception
     */
    public Camp createCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID) throws DuplicateEntityExistsException {
        Camp camp = new Camp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInChargeID, true);
        try {
            campRepository.add(camp);
        } catch (DuplicateEntityExistsException e) {
            System.out.println("Failed to add entity: " + e.getMessage());
            throw e;
        }
        return camp;
    }

    /**
     * Edit camp camp.
     *
     * @param camp the camp
     * @return the camp
     */
    public Camp editCamp(Camp camp) {
        try {
            campRepository.updateCamp(camp);
            return camp;
        } catch (EntityNotFoundException e) {
            System.out.println("Failed to update entity: " + e.getMessage());
            return null;

        }
    }


    /**
     * Edit camp camp.
     *
     * @param campName                the camp name
     * @param description             the description
     * @param campStartDate           the camp start date
     * @param campEndDate             the camp end date
     * @param campRegistrationEndDate the camp registration end date
     * @param userGroupOpenTo         the user group open to
     * @param location                the location
     * @param totalSlots              the total slots
     * @param campCommitteeSlots      the camp committee slots
     * @param staffInChargeID         the staff in charge id
     * @return the camp
     * @throws EntityNotFoundException the entity not found exception
     */
    public Camp editCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID) throws EntityNotFoundException {

        Camp newCamp = new Camp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInChargeID, true);

        try {
            campRepository.update(newCamp);
            return newCamp;
        } catch (EntityNotFoundException e) {
            System.out.println("Failed to update entity: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Delete camp.
     *
     * @param campId the camp id
     */
    public void deleteCamp(String campId) {
        try {
            campRepository.remove(campId);
        } catch (EntityNotFoundException e) {
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
    }

//    public boolean confict

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
     * Is time conflict boolean.
     *
     * @param camp      the camp
     * @param studentId the student id
     * @return the boolean
     */
    public boolean isTimeConflict(Camp camp, String studentId) {
        List<String> studentRegistered = getStudentRegistered(studentId);
        if (isTimeConflict(camp, studentRegistered)) return true;
        return false;

    }

    /**
     * Is before deadline boolean.
     *
     * @param campRegistrationEndDate the camp registration end date
     * @return the boolean
     */
    public boolean isBeforeDeadline(LocalDate campRegistrationEndDate) {
        LocalDate now = LocalDate.now();
        return !now.isAfter(campRegistrationEndDate);
    }

    /**
     * Add student to camp.
     *
     * @param newCampAttendee the new camp attendee
     * @throws CampConflictException the camp conflict exception
     */
    public void addStudentToCamp(CampAttendee newCampAttendee) throws CampConflictException {

        //查看是否过了报名时间
        Camp camp = getCampById(newCampAttendee.getCampId());
        if (isBeforeDeadline(camp.getCampRegistrationEndDate())) {
            throw new CampConflictException("The registration deadline for this camp has expired");
        }
        ArrayList<String> committeeRegistered = camp.getCommitteeRegistered();
        ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
        ArrayList<Student> studentsWithdraw = camp.getStudentsWithdraw();
        //查看之前是否注册
        for (Student student : studentsRegistered) {
            if (student.getID().equals(newCampAttendee.getStudentid())) {
                throw new CampConflictException("You have previously registered as a member, registration is not allowed");
            }
        }
        for (String committee : committeeRegistered) {
            if (committee.equals(newCampAttendee.getStudentid())) {
                throw new CampConflictException("You have previously registered as a committee,registration is not allowed");
            }
        }
        //查看是否被禁止参与了
        for (Student student : studentsWithdraw) {
            if(student.getID().equals(newCampAttendee.getStudentid())){
                throw new CampConflictException("You have previously registered and withdraw, registration is not allowed");
            }
        }
        //查看时间冲突
        List<String> studentRegistered = getStudentRegistered(newCampAttendee.getStudentid());
        if (isTimeConflict(camp, studentRegistered)) {
            throw new CampConflictException("It's the time conflict, registration is not allowed");
        }

        try {
            campAttendeeRepository.add(newCampAttendee);
        } catch (DuplicateEntityExistsException e) {
            e.printStackTrace();
        }
        setStudentsCommitteeRegistered();
    }

    /**
     * Gets camp by student.
     *
     * @param student the student
     * @return the camp by student
     */
    public List<String> getCampByStudent(Student student) {
        List<Entity> attendeeRepositoryAll = campAttendeeRepository.getAll();
        List<String> campByStudentRegis = new ArrayList<>();
        for (Entity entity : attendeeRepositoryAll) {
            CampAttendee attendee = (CampAttendee) entity;
            if (attendee.getStudentid().equals(student.getID()) && !attendee.isForbid()) {
                campByStudentRegis.add(attendee.getCampId());
            }
        }
        return campByStudentRegis;
    }

    /**
     * Gets camp id camp name.
     *
     * @return the camp id camp name
     */
    public Map<String, String> getCampIdCampName() {
        List<Camp> campList = campRepository.getAllCamps();
        Map<String, String> CampIdwithCampName = new HashMap<>();
        for (Camp camp : campList) {
            CampIdwithCampName.put(camp.getID(), camp.getCampName());
        }
        return CampIdwithCampName;
    }

    /**
     * With draw by student.
     *
     * @param campAttendee the camp attendee
     */
    public void withDrawByStudent(CampAttendee campAttendee) {
        for (Entity entity : campAttendeeRepository.getAll()) {
            CampAttendee campAttend = (CampAttendee) entity;
            if (campAttend.getCampId().equals(campAttendee.getCampId()) &&
                    campAttend.getStudentid().equals(campAttendee.getStudentid()))
                campAttendee.setID(campAttend.getID());
        }

        campAttendeeRepository.saveCampAttendee(campAttendee);
        setStudentsCommitteeRegistered();
    }

    /**
     * Sets students committee registered.
     */
    public void setStudentsCommitteeRegistered() {
        List<CampAttendee> allCampAttendee = campAttendeeRepository.getAllCampAttendee();
        List<Committee> allCommittee = committeeRepository.getAllCommittee();
        List<Camp> allCamps = campRepository.getAllCamps();

        for (Camp camp : allCamps) {
            String campId = camp.getID();
            for (CampAttendee campAttendee : allCampAttendee) {
                if (campAttendee.getCampId().equals(campId)) {
                    Student student = null;
                    try {
                        student = studentRepository.getStudentByID(campAttendee.getStudentid());
                    } catch (EntityNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(!campAttendee.isForbid()) {
                        camp.getStudentsRegistered().add(student);
                    }else{
                        camp.getStudentsWithdraw().add(student);
                    }
                }
            }
            for (Committee committee : allCommittee) {
                if (committee.getCampId().equals(campId)) {
                    camp.getCommitteeRegistered().add(committee.getStudentid());
                }
            }
        }
    }

    /**
     * Gets student registered.
     *
     * @param studentId the student id
     * @return the student registered
     */
    public List<String> getStudentRegistered(String studentId) {
        List<CampAttendee> allCampAttendee = campAttendeeRepository.getAllCampAttendee();
        List<String> studentRegisteredCamp = new ArrayList<>();
        for (CampAttendee campAttendee : allCampAttendee) {
            if (campAttendee.getStudentid().equals(studentId)) {
                studentRegisteredCamp.add(campAttendee.getCampId());
            }
        }
        return studentRegisteredCamp;
    }


}
