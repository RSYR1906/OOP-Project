package org.sc2002.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.sc2002.utils.exception.*;

public class Camp implements Entity{

    private String campName;
    private String description;

    private LocalDate campStartDate;
    private LocalDate campEndDate;
    private LocalDate campRegistrationEndDate;

    private Faculty userGroupOpenTo;
    private String location;

    private String staffInChargeID;

    private boolean visibilityToStudent = true;

    private int totalSlots;
    private int campCommitteeSlots;

    private ArrayList<Student> studentsRegistered;
    
    private ArrayList<String> committeeRegistered;

    private ArrayList<Student> studentBlacklist;


    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getID() {
        return campName;
    } // ID to use unique campName

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCampStartDate() {
        return campStartDate;
    }

    public void setCampStartDate(LocalDate campStartDate) {
        this.campStartDate = campStartDate;
    }

    public LocalDate getCampEndDate() {
        return campEndDate;
    }

    public void setCampEndDate(LocalDate campEndDate) {
        this.campEndDate = campEndDate;
    }

    public LocalDate getCampRegistrationEndDate() {
        return campRegistrationEndDate;
    }

    public void setCampRegistrationEndDate(LocalDate campRegistrationEndDate) {
        this.campRegistrationEndDate = campRegistrationEndDate;
    }

    public Faculty getUserGroupOpenTo() {
        return userGroupOpenTo;
    }

    public void setUserGroupOpenTo(Faculty userGroupOpenTo) {
        this.userGroupOpenTo = userGroupOpenTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlots = campCommitteeSlots;
    }

    public String getStaffInChargeID() {
        return staffInChargeID;
    }

    public void setStudentsRegistered(ArrayList<Student> studentsArray) {
        this.studentsRegistered = studentsArray;
    }

    public ArrayList<String> getCommitteeRegistered() {
        return committeeRegistered;
    }

    public void setCommitteeRegistered(ArrayList<String> committeeRegistered) {
        this.committeeRegistered = committeeRegistered;
    }

    public void setStaffInChargeID(String staffInChargeID) {
        this.staffInChargeID = staffInChargeID;
    }

    public boolean getVisibilityToStudent() {
        return visibilityToStudent;
    }

    public void setVisibilityToStudent(boolean visibilityToStudent) {
        this.visibilityToStudent = visibilityToStudent;
    }

    public Camp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, Boolean visibilityToStudent) {
        this.campName = campName;
        this.description = description;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationEndDate = campRegistrationEndDate;
        this.userGroupOpenTo = userGroupOpenTo;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        
        this.studentsRegistered = new ArrayList<>();
        this.studentBlacklist = new ArrayList<>();
        this.committeeRegistered = new ArrayList<>();
        this.staffInChargeID = staffInChargeID;
        this.visibilityToStudent = visibilityToStudent;
    }
    
    public Camp() {
    }

    public void registerStudent(Student student) throws CampFullException, BlacklistedStudentException {
        if (studentsRegistered.size() >= (totalSlots - campCommitteeSlots)) {
            throw new CampFullException("No available slots to register for the camp.");
        }

        if (studentBlacklist.contains(student)){
            throw new BlacklistedStudentException();
        }
        studentsRegistered.add(student);
    }

    public void registerCampCommitteeMember(String student) throws CampFullException, BlacklistedStudentException {
        if (committeeRegistered.size() >= campCommitteeSlots) {
            throw new CampFullException("No available slots to register for the camp.");
        }

        if (studentBlacklist.contains(student)){
            throw new BlacklistedStudentException();
        }
        committeeRegistered.add(student);
    }
    
    
    public void withdrawStudent(Student student) throws EntityNotFoundException {
        boolean isRemoved = studentsRegistered.remove(student);
        if (!isRemoved) {
            throw new EntityNotFoundException("Student is not registered for this camp.");
        }
        studentBlacklist.add(student);
        totalSlots++;
    }

    public ArrayList<Student> getStudentsRegistered() {
        return studentsRegistered;
    }

    public ArrayList<Student> getStudentBlacklist() {
        return studentBlacklist;
    }


    public boolean canStudentRegister(Student student) throws RegistrationClosedException, FacultyNotEligibleException, CampFullException, BlacklistedStudentException {
        LocalDate now = LocalDate.now();
//        if(now.isAfter(campRegistrationEndDate)){ //comment out for test
//            throw new RegistrationClosedException();
//        }
        if(student.getFaculty() != userGroupOpenTo && userGroupOpenTo != Faculty.ALL){
            throw new FacultyNotEligibleException();
        }
        if (studentsRegistered.size() >= totalSlots) {
            throw new CampFullException();
        }
        if (studentBlacklist.contains(student)){
            throw new BlacklistedStudentException();
        }
        return true;
    }

    public boolean canCampCommitteeMemberRegister(Student student) throws RegistrationClosedException, FacultyNotEligibleException, CampFullException, BlacklistedStudentException {
        LocalDate now = LocalDate.now();
//        if(now.isAfter(campRegistrationEndDate)){ //comment out for test
//            throw new RegistrationClosedException();
//        }
        if(student.getFaculty() != userGroupOpenTo && userGroupOpenTo != Faculty.ALL){
            throw new FacultyNotEligibleException();
        }
        if (committeeRegistered.size() >= campCommitteeSlots) {
            throw new CampFullException();
        }
        if (studentBlacklist.contains(student)){
            throw new BlacklistedStudentException();
        }
        return true;
    }

    public String toStringWithSeparator(String separator){
        StringBuilder sb = new StringBuilder();
        sb.append(campName).append(separator)
                .append(description).append(separator)
                .append(campStartDate).append(separator)
                .append(campEndDate).append(separator)
                .append(campRegistrationEndDate).append(separator)
                .append(userGroupOpenTo).append(separator)
                .append(location).append(separator)
                .append(totalSlots).append(separator)
                .append(campCommitteeSlots).append(separator)
                .append(staffInChargeID).append(separator)
                .append(visibilityToStudent);
        return sb.toString();

    }

    public void registerCampCommitteeMember(Student student) {
    }
}
