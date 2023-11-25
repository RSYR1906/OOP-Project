package org.sc2002.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import org.sc2002.entity.*;
import org.sc2002.repository.*;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.WrongStaffException;
import org.sc2002.utils.exception.DuplicateEntityExistsException;

/**
 * The type Staff controller.
 */
public class StaffController {

    private CampController campController;
    private CampRepository campRepository;
    private StaffRepository staffRepository;
    private EnqueryRepository enqueryRepository;
    private EnqueryController enqueryController;

    /**
     * Instantiates a new Staff controller.
     */
    public StaffController(){

    }

    /**
     * Instantiates a new Staff controller.
     *
     * @param campController    the camp controller
     * @param campRepository    the camp repository
     * @param staffRepository   the staff repository
     * @param enqueryRepository the enquery repository
     */
    public StaffController(CampController campController, CampRepository campRepository, StaffRepository staffRepository,EnqueryRepository enqueryRepository, EnqueryController enqueryController) {
        this.campController = campController;
        this.campRepository = campRepository;
        this.staffRepository = staffRepository;
        this.enqueryRepository=enqueryRepository;
        this.enqueryController = enqueryController;;
    }

    /**
     * Create camp camp.
     *
     * @param camp the camp
     * @return the camp
     */
    public  Camp createCamp(Camp camp){
        campController.createCamp(camp);
        return camp;
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
     * @param visibilityToStudent     the visibility to student
     * @return the camp
     * @throws WrongStaffException     the wrong staff exception
     * @throws EntityNotFoundException the entity not found exception
     */
// Method to edit an existing camp
    public Camp editCamp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID,boolean visibilityToStudent) throws WrongStaffException, EntityNotFoundException {
        // Check if the staff is the owner of the camp
        if (campRepository.getCampByID(campName).getStaffInChargeID().equals(staffInChargeID)) {
            Camp camp = new Camp(campName, description, campStartDate, campEndDate, campRegistrationEndDate, userGroupOpenTo, location, totalSlots, campCommitteeSlots, staffInChargeID,visibilityToStudent);
            Camp editedCamp = campController.editCamp(camp);
            staffRepository.getStaffByID(staffInChargeID).deleteFromCreatedCamps(editedCamp);
            staffRepository.getStaffByID(staffInChargeID).addToCreatedCamps(editedCamp);
            return editedCamp;
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
            // Handle the error or provide appropriate feedback
            //return null;
        }
    }

    /**
     * Delete camp.
     *
     * @param campID          the camp id
     * @param staffInChargeID the staff in charge id
     * @throws WrongStaffException     the wrong staff exception
     * @throws EntityNotFoundException the entity not found exception
     */
    public void deleteCamp(String campID, String staffInChargeID) throws WrongStaffException, EntityNotFoundException {
        if (campRepository.getCampByID(campID).getStaffInChargeID().equals(staffInChargeID)) {
            Camp campToDelete = campRepository.getCampByID(campID);
            campController.deleteCamp(campID);
            // Remove the camp from the staff's created camps
            staffRepository.getStaffByID(staffInChargeID).deleteFromCreatedCamps(campToDelete);

            // Remove the camp from the list of all camps
            campController.deleteCamp(campToDelete.getID());

            return;
        } else {
            // Camp does not belong to the staff
            throw new WrongStaffException("Camp does not belong to the staff.");
        }
    }

    /**
     * Change visibility.
     *
     * @param staff  the staff
     * @param campId the camp id
     * @throws EntityNotFoundException the entity not found exception
     */
    public void changeVisibility(Staff staff, String campId) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        for (Camp camp : camps) {
            if (camp.getID().equals(campId)) {
                boolean visibilityToStudent = camp.getVisibilityToStudent();
                if (visibilityToStudent) {
                    camp.setVisibilityToStudent(false);
                } else {
                    camp.setVisibilityToStudent(true);
                }
                campRepository.saveCamp(camp);
            }
        }
    }

    /**
     * View all enquery array list.
     *
     * @param staff the staff
     * @return the array list
     * @throws EntityNotFoundException the entity not found exception
     */
    public ArrayList<String> viewAllEnquery(Staff staff) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        ArrayList<Enquery> allEnquery = new ArrayList<>();
        for (Camp camp : camps) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            allEnquery.addAll(enqueryList);
        }
        ArrayList<String> queryList = new ArrayList<>();
        for (Enquery enquery : allEnquery) {
            String query = enquery.getQuery();
            queryList.add(query);
        }
        return queryList;
    }

    /**
     * Answer my questions.
     *
     * @param staff   the staff
     * @param campId  the camp id
     * @param Message the message
     * @throws EntityNotFoundException the entity not found exception
     */
    public void answerMyQuestions(Staff staff, String campId, String Message) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        ArrayList<Enquery> allEnquery = new ArrayList<>();
        for (Camp camp : camps) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            allEnquery.addAll(enqueryList);
        }
        for (Enquery enquery : allEnquery) {
            if (enquery.getCampId() == campId) {
                enquery.setAnswer(Message);
                enquery.setAnswerStaff(staff.getID());
            }
        }
    }

    /**
     * View all suggestions map.
     *
     * @param staff the staff
     * @return the map
     * @throws EntityNotFoundException the entity not found exception
     */
    public Map<String, String> viewAllSuggestions(Staff staff) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        Map<String, String> suggestionMap = new HashMap<>();
        for (Camp camp : camps) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            if (enqueryList==null){
                System.out.println("not exit Suggestion");
                return suggestionMap;
            }
            for (Enquery enquery : enqueryList) {
                String suggestion = enquery.getSuggestion();
                suggestionMap.put(enquery.getID(), suggestion);
            }
        }
        return suggestionMap;
    }

    public List<Enquery> getSuggestions(Staff staff) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        List<Enquery> suggestionofEnquery = new ArrayList<>();
        for (Camp camp : camps) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            if (enqueryList==null){
                System.out.println("not exit Suggestion");
                return null;
            }
            for (Enquery enquery : enqueryList) {
                if(!enquery.getSuggestion().equals("null") && !enquery.getIsApprove())
                suggestionofEnquery.add(enquery);
            }
        }
        return suggestionofEnquery;
    }

    /**
     * Approve suggestions.
     *
     * @param enqueryId the enquery id
     * @param staff     the staff
     * @throws EntityNotFoundException the entity not found exception
     */
    public void approveSuggestions(String enqueryId, Staff staff) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        ArrayList<Enquery> allEnquery = new ArrayList<>();
        for (Camp camp : camps) {
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            allEnquery.addAll(enqueryList);
        }
        for (Enquery enquery : allEnquery) {
            if (enquery.getID().equals(enqueryId) ) {
                enquery.setIsApprove(true);
            }
        }
    }

    public void approveSuggestions(Enquery Suggestion){
        enqueryController.editEnquery(Suggestion);
    }

    /**
     * Create report.
     *
     * @param staff the staff
     * @throws EntityNotFoundException the entity not found exception
     */
//TODO
    public void createReport(Staff staff) throws EntityNotFoundException {
        List<Camp> camps = viewAllCampsICreated(staff.getID());
        List<Camp> newCamp = new ArrayList<>();
        for (Camp camp : camps) {
            Camp temp = new Camp();
            ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
            String campName = camp.getCampName();
            ArrayList<String> committeeRegistered = camp.getCommitteeRegistered();
            String staffInChargeID = camp.getStaffInChargeID();
            temp.setStudentsRegistered(studentsRegistered);
            temp.setCampName(campName);
            temp.setCommitteeRegistered(committeeRegistered);
            temp.setStaffInChargeID(staffInChargeID);
            newCamp.add(temp);
        }

        String csvFilePath = "campReport.csv";

        // 生成CSV文件
        try (FileWriter writer = new FileWriter(csvFilePath)) {

            writer.append("Camp Name,Staff In Charge ID,Students Registered,Committee Registered\n");


            for (Camp camp : newCamp) {
                StringBuilder rowBuilder = new StringBuilder();
                rowBuilder.append(camp.getCampName()).append(",");
                rowBuilder.append(camp.getStaffInChargeID()).append(",");

                // 将Students Registered拼接为一个以逗号分隔的字符串
                ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
                if (studentsRegistered != null && !studentsRegistered.isEmpty()) {
                    StringBuilder studentsBuilder = new StringBuilder();
                    for (Student student : studentsRegistered) {
                        studentsBuilder.append(student.getName()).append(" ");
                    }
                    studentsBuilder.deleteCharAt(studentsBuilder.length() - 1);
                    rowBuilder.append(studentsBuilder.toString());
                }
                rowBuilder.append(",");

                ArrayList<String> committeeRegistered = camp.getCommitteeRegistered();
                if (committeeRegistered != null && !committeeRegistered.isEmpty()) {
                    StringBuilder committeeBuilder = new StringBuilder();
                    for (String committee : committeeRegistered) {
                        committeeBuilder.append(committee).append(" ");
                    }
                    committeeBuilder.deleteCharAt(committeeBuilder.length() - 1);
                    rowBuilder.append(committeeBuilder.toString());
                }

                rowBuilder.append("\n");
                writer.append(rowBuilder.toString());
            }

            System.out.println("CSV file has been generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate CSV file: " + e.getMessage());
        }
    }

    /**
     * View all student map.
     *
     * @return the map
     */
    public Map<String, ArrayList<Student>> viewAllStudent() {
        HashMap<String, ArrayList<Student>> report = new HashMap<>();
        List<Camp> camps = viewAllCamps();
        for (Camp camp : camps) {
            ArrayList<Student> studentsRegistered = camp.getStudentsRegistered();
            report.put(camp.getCampName(), studentsRegistered);
        }
        return report;
    }

    /**
     * View all camps list.
     *
     * @return the list
     */
// Method to view all camps
    public List<Camp> viewAllCamps() {
        return campRepository.getAllCamps();
    }

    /**
     * View all camps i created list.
     *
     * @param staffID the staff id
     * @return the list
     * @throws EntityNotFoundException the entity not found exception
     */
// Method to view all camps created by the staff
    public List<Camp> viewAllCampsICreated(String staffID) throws EntityNotFoundException {
        List<Entity> entities = campRepository.getAll();
        List<Camp> camps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Camp && campRepository.getCampByCampID(entity.getID()).getStaffInChargeID().equals(staffID)) {
                camps.add((Camp) entity);
            }
        }
        return camps;
    }

    /**
     * View reply map.
     *
     * @param staff    the staff
     * @param campName the camp name
     * @return the map
     */
    public Map<String, String> viewReply(Staff staff,String campName) {
        List<Camp> allCamps = campRepository.getAllCamps();
        Map<String, String> reply = new HashMap<>();
        for (Camp camp : allCamps) {
            if (camp.getCampName().equals(campName)) {
                List<Enquery> allEnquery = enqueryRepository.getAllEnquery();
                for (Enquery enquery : allEnquery) {
                    if(enquery.getCampId().equals(camp.getID())){
                        reply.put(campName,enquery.getReply());
                    }
                }
            }
        }
        return reply;
    }

    /**
     * Edit reply.
     *
     * @param staff    the staff
     * @param campName the camp name
     * @param message  the message
     * @throws EntityNotFoundException the entity not found exception
     */
    public void editReply(Staff staff,String campName, String message) throws EntityNotFoundException {
        Camp camp = null;
        for (Camp onecamp : campRepository.getAllCamps()) {
            if(onecamp.getCampName().equals(campName)){
                camp=onecamp;
            }
        }
        if(camp==null){
            System.out.println("the camp not exit");
            return;
        }
        if(camp.getStaffInChargeID().equals(staff.getID())){
            ArrayList<Enquery> enqueryList = camp.getEnqueryList();
            if(enqueryList==null){
                Enquery enquery=new Enquery();
                enquery.setReply(message);
                enquery.setCampId(camp.getCampName());
                enquery.setAnswerStaff(staff.getID());
                enquery.setIsApprove(false);
                try {
                    enqueryRepository.add(enquery);
                } catch (DuplicateEntityExistsException e) {
                    System.out.println("add error");
                }
                return;
            }
            for (Enquery enquery : enqueryList) {
                if(enquery.getAnswerStaff()==staff.getID()){
                    enquery.setReply(message);
                    enqueryRepository.saveEnquery(enquery);
                }
            }
        }
    }
}
