package org.sc2002.entity;

import java.util.Random;

public class Suggestion implements Entity{

    private String suggestionID;
    private String staffID;
    private Student student;
    private Camp camp;
    private String suggestion;
    private Boolean isApproved;

    public Suggestion(Student student, Camp camp, String suggestion) {
        this.suggestionID = getRandomID();
        this.student = student;
        this.camp = camp;
        this.staffID = camp.getStaffInChargeID();
        this.suggestion = suggestion;
        this.isApproved = false;
    }

    public Suggestion(String suggestionID, String staffID, Student student, Camp camp, String suggestion, Boolean isApproved) {
        this.suggestionID = suggestionID;
        this.staffID = staffID;
        this.student = student;
        this.camp = camp;
        this.suggestion = suggestion;
        this.isApproved = isApproved;
    }

    @Override
    public String getID() {
        return suggestionID;
    }

    /**
     * Gets random id.
     *
     * @return the random id
     */
    public String getRandomID() {
        int length = 8;
        long timestamp = System.currentTimeMillis();
        Random random = new Random(timestamp);

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();

    }

    public String getSuggestionID() {
        return suggestionID;
    }

    public String getStaffID() {
        return staffID;
    }

    public Student getStudent() {
        return student;
    }

    public Camp getCamp() {
        return camp;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }
}
