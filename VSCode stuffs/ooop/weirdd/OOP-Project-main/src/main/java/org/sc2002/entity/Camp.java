package org.sc2002.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sc2002.utils.RandomIDGenerator;
import org.sc2002.utils.exception.EntityNotFoundException;
import org.sc2002.utils.exception.CampFullException;

/**
 * The type Camp.
 */
public class Camp implements Entity {
    private String id;
    private String campName;
    private String description;
    private LocalDate campStartDate;
    private LocalDate campEndDate;
    private LocalDate campRegistrationEndDate;
    private Faculty userGroupOpenTo;
    private String location;
    private int totalSlots;
    private int campCommitteeSlots;
    private String staffInChargeID;

    private boolean visibilityToStudent = true;

    private ArrayList<Student> studentsRegistered;

    private ArrayList<String> committeeRegistered;

    private ArrayList<Enquery> enqueryList;

    private ArrayList<Student> studentsWithdraw;

    /**
     * Instantiates a new Camp.
     */
    public Camp() {
        this.id = RandomIDGenerator.getRandomID();
    }


    /**
     * Instantiates a new Camp.
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
     */
    public Camp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, boolean visibilityToStudent) {
        this.campName = campName;
        this.description = description;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationEndDate = campRegistrationEndDate;
        this.userGroupOpenTo = userGroupOpenTo;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.visibilityToStudent = visibilityToStudent;
        this.studentsRegistered = new ArrayList<>();
        this.committeeRegistered = new ArrayList<>();
        this.studentsWithdraw = new ArrayList<>();
        this.enqueryList = new ArrayList<>();
        this.staffInChargeID = staffInChargeID;

    }

    /**
     * Instantiates a new Camp.
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
     * @param committeeRegistered     the committee registered
     */
    public Camp(String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, boolean visibilityToStudent, ArrayList<String> committeeRegistered) {
        this.campName = campName;
        this.description = description;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationEndDate = campRegistrationEndDate;
        this.userGroupOpenTo = userGroupOpenTo;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.visibilityToStudent = visibilityToStudent;
        this.studentsRegistered = new ArrayList<>();
        this.studentsWithdraw = new ArrayList<>();
        this.enqueryList = new ArrayList<>();
        this.staffInChargeID = staffInChargeID;
        this.committeeRegistered = committeeRegistered;
    }

    /**
     * Instantiates a new Camp.
     *
     * @param campId                  the camp id
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
     */
    public Camp(String campId, String campName, String description, LocalDate campStartDate, LocalDate campEndDate, LocalDate campRegistrationEndDate, Faculty userGroupOpenTo, String location, int totalSlots, int campCommitteeSlots, String staffInChargeID, boolean visibilityToStudent) {
        this.id = campId;
        this.campName = campName;
        this.description = description;
        this.campStartDate = campStartDate;
        this.campEndDate = campEndDate;
        this.campRegistrationEndDate = campRegistrationEndDate;
        this.userGroupOpenTo = userGroupOpenTo;
        this.location = location;
        this.totalSlots = totalSlots;
        this.campCommitteeSlots = campCommitteeSlots;
        this.visibilityToStudent = visibilityToStudent;
        this.studentsRegistered = new ArrayList<>();
        this.committeeRegistered = new ArrayList<>();
        this.studentsWithdraw = new ArrayList<>();
        this.enqueryList = new ArrayList<>();
        this.staffInChargeID = staffInChargeID;
    }

    /**
     * Gets enquery list.
     *
     * @return the enquery list
     */
    public ArrayList<Enquery> getEnqueryList() {
        return this.enqueryList;
    }

    /**
     * Sets enquery list.
     *
     * @param enqueryList the enquery list
     */
    public void setEnqueryList(ArrayList<Enquery> enqueryList) {
        this.enqueryList = enqueryList;
    }

    /**
     * Gets visibility to student.
     *
     * @return the visibility to student
     */
    public boolean getVisibilityToStudent() {
        return visibilityToStudent;
    }

    /**
     * Sets visibility to student.
     *
     * @param visibility the visibility
     */
    public void setVisibilityToStudent(boolean visibility) {
        this.visibilityToStudent = visibility;
    }

    /**
     * Gets camp name.
     *
     * @return the camp name
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Sets camp name.
     *
     * @param campName the camp name
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    @Override
    public String getID() {
        return id;
    } // ID to use unique campName

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets camp start date.
     *
     * @return the camp start date
     */
    public LocalDate getCampStartDate() {
        return campStartDate;
    }

    /**
     * Sets camp start date.
     *
     * @param campStartDate the camp start date
     */
    public void setCampStartDate(LocalDate campStartDate) {
        this.campStartDate = campStartDate;
    }

    /**
     * Gets camp end date.
     *
     * @return the camp end date
     */
    public LocalDate getCampEndDate() {
        return campEndDate;
    }

    /**
     * Sets camp end date.
     *
     * @param campEndDate the camp end date
     */
    public void setCampEndDate(LocalDate campEndDate) {
        this.campEndDate = campEndDate;
    }

    /**
     * Gets camp registration end date.
     *
     * @return the camp registration end date
     */
    public LocalDate getCampRegistrationEndDate() {
        return campRegistrationEndDate;
    }

    /**
     * Sets camp registration end date.
     *
     * @param campRegistrationEndDate the camp registration end date
     */
    public void setCampRegistrationEndDate(LocalDate campRegistrationEndDate) {
        this.campRegistrationEndDate = campRegistrationEndDate;
    }

    /**
     * Gets user group open to.
     *
     * @return the user group open to
     */
    public Faculty getUserGroupOpenTo() {
        return userGroupOpenTo;
    }

    /**
     * Sets user group open to.
     *
     * @param userGroupOpenTo the user group open to
     */
    public void setUserGroupOpenTo(Faculty userGroupOpenTo) {
        this.userGroupOpenTo = userGroupOpenTo;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets total slots.
     *
     * @return the total slots
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Sets total slots.
     *
     * @param totalSlots the total slots
     */
    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    /**
     * Gets camp committee slots.
     *
     * @return the camp committee slots
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    /**
     * Sets camp committee slots.
     *
     * @param campCommitteeSlots the camp committee slots
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        if (campCommitteeSlots < 0 || campCommitteeSlots > 10) {

            throw new IllegalArgumentException("campCommitteeSlots必须小于等于10");
        }
        this.campCommitteeSlots = campCommitteeSlots;
    }

    /**
     * Gets staff in charge id.
     *
     * @return the staff in charge id
     */
    public String getStaffInChargeID() {
        return staffInChargeID;
    }

    /**
     * Sets students registered.
     *
     * @param studentsArray the students array
     */
    public void setStudentsRegistered(ArrayList<Student> studentsArray) {
        this.studentsRegistered = studentsArray;
    }

    /**
     * Gets committee registered.
     *
     * @return the committee registered
     */
    public ArrayList<String> getCommitteeRegistered() {
        return committeeRegistered;
    }


    /**
     * Sets committee registered.
     *
     * @param committeeRegistered the committee registered
     */
    public void setCommitteeRegistered(ArrayList<String> committeeRegistered) {
        this.committeeRegistered = committeeRegistered;
    }


    /**
     * Sets students withdraw.
     *
     * @param student the student
     */
    public void setStudentsWithdraw(Student student) {
        this.studentsWithdraw.add(student);
    }


    /**
     * Register student.
     *
     * @param student the student
     * @throws CampFullException the camp full exception
     */
    public void registerStudent(Student student) throws CampFullException {
        if (studentsRegistered.size() < totalSlots) {
            studentsRegistered.add(student);
        } else {
            throw new CampFullException("No available slots to register for the camp.");
        }
    }

    /**
     * Register committee.
     *
     * @param student the student
     * @throws CampFullException the camp full exception
     */
    public void registerCommittee(Student student) throws CampFullException {
        if (committeeRegistered.size() < campCommitteeSlots) {
            committeeRegistered.add(student.getID());
        } else {
            throw new CampFullException("No available slots to register for the camp.");
        }
    }

    /**
     * Withdraw committee.
     *
     * @param student the student
     * @throws EntityNotFoundException the entity not found exception
     */
    public void withdrawCommittee(Student student) throws EntityNotFoundException {
        boolean isRemoved = committeeRegistered.remove(student);
        if (!isRemoved) {
            throw new EntityNotFoundException("Student is not registered for the committee of this camp.");
        }
        this.studentsWithdraw.add(student);
    }

    /**
     * Withdraw student.
     *
     * @param student the student
     * @throws EntityNotFoundException the entity not found exception
     */
    public void withdrawStudent(Student student) throws EntityNotFoundException {
        boolean isRemoved = studentsRegistered.remove(student);
        if (!isRemoved) {
            throw new EntityNotFoundException("Student is not registered for this camp.");
        }
    }

    /**
     * Gets students registered.
     *
     * @return the students registered
     */
    public ArrayList<Student> getStudentsRegistered() {
        return studentsRegistered;
    }

    /**
     * Can student register boolean.
     *
     * @param student the student
     * @return the boolean
     */
    public boolean canStudentRegister(Student student) {
        LocalDate now = LocalDate.now();
        boolean isBeforeDeadline = !now.isAfter(campRegistrationEndDate);
        boolean isFacultyAllowed = student.getFaculty() == userGroupOpenTo || userGroupOpenTo == null; // Assuming null means open to all faculties
        return isBeforeDeadline && isFacultyAllowed && studentsRegistered.size() < totalSlots;
    }

    /**
     * To string with separator string.
     *
     * @param separator the separator
     * @return the string
     */
    public String toStringWithSeparator(String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(separator)
                .append(campName).append(separator)
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

    /**
     * Is visibility to stu boolean.
     *
     * @return the boolean
     */
    public boolean isVisibilityToStu() {
        return visibilityToStudent;
    }

    /**
     * Sets staff in charge id.
     *
     * @param staffInChargeID the staff in charge id
     */
    public void setStaffInChargeID(String staffInChargeID) {
        this.staffInChargeID = staffInChargeID;
    }

    /**
     * Sets students withdraw.
     *
     * @param studentsWithdraw the students withdraw
     */
    public void setStudentsWithdraw(ArrayList<Student> studentsWithdraw) {
        this.studentsWithdraw = studentsWithdraw;
    }

    /**
     * Gets students withdraw.
     *
     * @return the students withdraw
     */
    public ArrayList<Student> getStudentsWithdraw() {
        return studentsWithdraw;
    }
}
