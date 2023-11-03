package org.sc2002.entity;

import java.time.LocalDate;

public class Camp implements Entity{

    private String campName;
    private String campID;
    private String description;

    private LocalDate campStartDate;
    private LocalDate campEndDate;
    private LocalDate campRegistrationEndDate;

    private Faculty userGroupOpenTo;
    private String location;

    private int totalSlots;
    private int campCommitteeSlots;

    //private Staff staffInCharge; // add after Staff implementation
    private Student[] studentsRegistered;
    private Student[] committeeRegistered;


    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getID() {
        return campName;
    } // to be changed to unique string

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

//    public Staff getStaffInCharge() {
//        return staffInCharge;
//    }
//
//    public void setStaffInCharge(Staff staffInCharge) {
//        this.staffInCharge = staffInCharge;
//    }

    public Student[] getStudentsRegistered() {
        return studentsRegistered;
    }

    public void setStudentsRegistered(Student[] studentsRegistered) {
        this.studentsRegistered = studentsRegistered;
    }

    public Student[] getCommitteeRegistered() {
        return committeeRegistered;
    }

    public void setCommitteeRegistered(Student[] committeeRegistered) {
        this.committeeRegistered = committeeRegistered;
    }

    public Camp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots) {
        this.campName = campName;
        this.description = description;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationEndDate = campRegistrationEndDate;
        this.userGroupOpenTo = userGroupOpenTo;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        //this.staffInCharge = staffInCharge;
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
                .append(campCommitteeSlots).append(separator);
                //.append(staffInCharge);
        return sb.toString();

    }
}
