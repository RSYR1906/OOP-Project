package org.sc2002.entity;

import java.util.Random;

public class Enquiry implements Entity{

    private String enquiryID;
    private String staffID;
    private Student student;
    private Camp camp;

    private String query;
    private String answer;

    public Enquiry(Student student, Camp camp, String query) {
        this.enquiryID = getRandomID();
        this.student = student;
        this.camp = camp;
        this.staffID = camp.getStaffInChargeID();
        this.query = query;
        this.answer = "NOT BEEN ANSWERED";
    }

    public Enquiry(String enquiryID, String staffID,  Student student, Camp camp, String query, String answer) {
        this.enquiryID = enquiryID;
        this.staffID = staffID;
        this.student = student;
        this.camp = camp;
        this.query = query;
        this.answer = answer;
    }

    @Override
    public String getID() {
        return enquiryID;
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

    public String getStaffID() {
        return staffID;
    }

    public Student getStudent() {
        return student;
    }

    public Camp getCamp() {
        return camp;
    }

    public String getQuery() {
        return query;
    }

    public String getAnswer() {
        return answer;
    }
}
