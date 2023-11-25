package org.sc2002.controller;

import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.CSVWriter;
import org.sc2002.utils.exception.CampConflictException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Committee controller.
 */
public class CommitteeController {

    private CampRepository campRepository;
    private CampAttendeeRepository campAttendeeRepository;
    private CommitteeRepository committeeRepository;
    private EnqueryRepository enqueryRepository;
    private StudentRepository studentRepository;
    private CampController campController;

    /**
     * Instantiates a new Committee controller.
     *
     * @param campRepository    the camp repository
     * @param enqueryRepository the enquery repository
     * @param studentRepository the student repository
     */
    public CommitteeController(CampRepository campRepository, EnqueryRepository enqueryRepository, StudentRepository studentRepository) {
        this.campRepository = campRepository;
        this.enqueryRepository = enqueryRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Instantiates a new Committee controller.
     *
     * @param campRepository         the camp repository
     * @param campAttendeeRepository the camp attendee repository
     * @param committeeRepository    the committee repository
     * @param enqueryRepository      the enquery repository
     * @param studentRepository      the student repository
     * @param campController         the camp controller
     */
    public CommitteeController(CampRepository campRepository, CampAttendeeRepository campAttendeeRepository, CommitteeRepository committeeRepository, EnqueryRepository enqueryRepository, StudentRepository studentRepository, CampController campController) {
        this.campRepository = campRepository;
        this.campAttendeeRepository = campAttendeeRepository;
        this.committeeRepository = committeeRepository;
        this.enqueryRepository = enqueryRepository;
        this.studentRepository = studentRepository;
        this.campController = campController;
    }

    /**
     * View all suggestions map.
     *
     * @param student the student
     * @return the map
     * @throws EntityNotFoundException the entity not found exception
     */
    public Map<String, String> viewAllSuggestions(Student student) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsIRegister(student.getID());
        Map<String, String> suggestionMap = new HashMap<>();
        for (Camp camp : camps) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            for (Enquery enquery : enqueryList) {
                if (enquery.getIsApprove()) {
                    continue;
                }
                String suggestion = enquery.getSuggestion();
                suggestionMap.put(camp.getID(), suggestion);
            }
        }
        return suggestionMap;
    }


    /**
     * View reply map.
     *
     * @param student the student
     * @return the map
     */
    public Map<String, String> viewReply(Student student) {
        String campCommitteeMember = student.getCampCommitteeMember();
        List<Camp> allCamps = campRepository.getAllCamps();
        Map<String, String> reply = new HashMap<>();
        for (Camp camp : allCamps) {
            if (camp.getCampName().equals(campCommitteeMember)) {
                for (Enquery enquery : camp.getEnqueryList()) {
                    if (!enquery.getReply().isEmpty() && enquery.getSuggestStudent().equals(student.getID())) {
                        reply.put(camp.getID(), enquery.getReply());
                    }
                }
            }
        }
        return reply;
    }

    /**
     * Edit suggestion.
     *
     * @param student the student
     * @param campId  the camp id
     * @param message the message
     * @throws EntityNotFoundException the entity not found exception
     */
    public void editSuggestion(Student student, String campId, String message) throws EntityNotFoundException {
        Camp camp = campRepository.getCampByID(campId);
        if (camp.getCommitteeRegistered().contains(student)) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            if (enqueryList == null) {
                enqueryList = new ArrayList<>();
            }
            for (Enquery enquery : enqueryList) {
                if (enquery.getIsApprove()) {
                    continue;
                }
                if (enquery.getSuggestStudent().isEmpty()) {
                    enquery.setSuggestStudent(student.getID());
                    enquery.setSuggestion(message);
                    student.setScore(student.getScore() + 1);
                    enqueryRepository.saveEnquery(enquery);
                    studentRepository.saveStudent(student);
                } else if (enquery.getSuggestStudent() == student.getID()) {
                    enquery.setSuggestion(message);
                    enqueryRepository.saveEnquery(enquery);
                }

            }
        }
    }


    /**
     * Edit reply.
     *
     * @param student  the student
     * @param campName the camp name
     * @param message  the message
     * @throws EntityNotFoundException the entity not found exception
     */
    public void editReply(Student student, String campName, String message) throws EntityNotFoundException {
        Camp camp = null;
        for (Camp onecamp : campRepository.getAllCamps()) {
            if (onecamp.getCampName().equals(campName)) {
                camp = onecamp;
            }
        }
        if (camp == null) {
            System.out.println("the camp not exit");
            return;
        }
        if (camp.getCommitteeRegistered().contains(student)) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            if (enqueryList == null) {
                Enquery enquery = new Enquery();
                enquery.setReply(message);
                enquery.setCampId(camp.getCampName());
                enquery.setAnswerStaff(student.getID());
                enquery.setIsApprove(false);
                try {
                    enqueryRepository.add(enquery);
                } catch (DuplicateEntityExistsException e) {
                    System.out.println("add error");
                }
                return;
            }
            for (Enquery enquery : enqueryList) {
                if (enquery.getSuggestStudent().isEmpty()) {
                    enquery.setSuggestStudent(student.getID());
                    enquery.setReply(message);
                    student.setScore(student.getScore() + 1);
                    enqueryRepository.saveEnquery(enquery);
                    studentRepository.saveStudent(student);
                } else if (enquery.getSuggestStudent() == student.getID()) {
                    enquery.setReply(message);
                    enqueryRepository.saveEnquery(enquery);
                }
            }
        }
    }


    /**
     * Delete suggestion.
     *
     * @param student the student
     * @param campId  the camp id
     * @throws EntityNotFoundException the entity not found exception
     */
    public void deleteSuggestion(Student student, String campId) throws EntityNotFoundException {
        Camp camp = campRepository.getCampByID(campId);
        if (camp.getCommitteeRegistered().contains(student)) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            for (Enquery enquery : enqueryList) {
                if (enquery.getIsApprove() || enquery.getSuggestStudent().isEmpty()) {
                    continue;
                }
                if (enquery.getSuggestStudent() == student.getID()) {
                    enquery.setSuggestion(null);
                    enquery.setSuggestStudent(null);
                }
            }
        }
    }

    /**
     * Create report.
     *
     * @param staff the staff
     * @throws EntityNotFoundException the entity not found exception
     */
//TODO
    public void createReport(Staff staff) throws EntityNotFoundException {
        String staffId = staff.getID();
        String fileName = "Committees"+"of"+staffId+".csv";

        List<Camp> camps = campRepository.getAllCamps();
        for (Camp camp : camps) {
            if(camp.getStaffInChargeID().equals(staffId)){

                ArrayList<String> studentIds = camp.getCommitteeRegistered();
                if(studentIds.size()>0){
                    CSVWriter.writeCSV("CampName:" + camp.getCampName(), fileName);
                    CSVWriter.writeCSV("----------------------------", fileName);
                    for (String studentId : studentIds) {
                        Student student = studentRepository.getStudentByID(studentId);
                        String temp = student.getID()+"," + String.valueOf(student.getScore());
                        CSVWriter.writeCSV(temp, fileName);
                    }
                }

            }


        }
        List<Camp> campList = new ArrayList<>();
//        for (Camp camp : allCamps) {
//            if (camp.getStaffInChargeID().equals(staffId)) {
//                campList.add(camp);
//            }
//        }
        for (Camp camp : campList) {
            ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
            String campName = camp.getCampName();
            ArrayList<String> committeeRegistered = camp.getCommitteeRegistered();
        }

    }





    /**
     * View all camps i register list.
     *
     * @param committeeId the committee id
     * @return the list
     * @throws EntityNotFoundException the entity not found exception
     */
// Method to view all camps created by the committee
    public List<Camp> viewAllCampsIRegister(String committeeId) throws EntityNotFoundException {
        List<Entity> entities = campRepository.getAll();
        List<Camp> camps = new ArrayList<>();
        for (Entity entity : entities) {
            Camp camp = (Camp) entity;
            if (camp.getCommitteeRegistered().contains(committeeId)) {
                camps.add(camp);
            }
        }
        return camps;
    }

    /**
     * Add student to committee.
     *
     * @param newCommittee the new committee
     * @throws CampConflictException the camp conflict exception
     */
    public void addStudentToCommittee(Committee newCommittee) throws CampConflictException {
        //查看是否被禁止参与了
        List<Entity> campAttendees = campAttendeeRepository.getAll();
        for (Entity campAttendee : campAttendees) {
            CampAttendee oldCampAttendee = (CampAttendee) campAttendee;
            if (oldCampAttendee.getCampId().equals(newCommittee.getCampId()) && oldCampAttendee.isForbid()) {
                throw new CampConflictException("You have previously registered and exited, registration is not allowed, registration is not allowed");

            }
        }

        //查看之前是否是commitee
        if (getCommitteeByStudent(newCommittee.getStudentid()).size() != 0) {
            throw new CampConflictException("You have previously registered as a committee, registration is not allowed");
        }

        Camp camp = campController.getCampById(newCommittee.getCampId());
        if (campController.isTimeConflict(camp, newCommittee.getStudentid())) {
            throw new CampConflictException("It's the time conflict, registration is not allowed");
        }


        try {
            committeeRepository.add(newCommittee);
        } catch (DuplicateEntityExistsException e) {
            e.printStackTrace();
        }
        campController.setStudentsCommitteeRegistered();

    }

    /**
     * Gets commitee by student.
     *
     * @param studentId the student id
     * @return Obtain the committee ID for student participation based on the studentId
     */
    public List<String> getCommitteeByStudent(String studentId) {
        List<Entity> committeeRepositoryAll = committeeRepository.getAll();
        List<String> committeeByStudentRegis = new ArrayList<>();
        for (Entity entity : committeeRepositoryAll) {
            Committee committee = (Committee) entity;
            if (committee.getStudentid().equals(studentId)) {
                committeeByStudentRegis.add(committee.getCampId());
            }
        }
        return committeeByStudentRegis;
    }

//    boolean isCommittee(){
//        return false;
//    }


}
