package org.sc2002.entity;

import java.util.Random;

/**
 * The type Enquery.
 */
public class Enquery implements Entity {
    private String id;
    private String query;
    private String answer;
    private String createStudent;
    private String suggestStudent;
    private String answerStaff;
    private String campId;
    private String suggestion;
    private String reply;
    private boolean isApprove = false;

    /**
     * Instantiates a new Enquery.
     */
    public Enquery() {
        this.id = getRandomID();
    }

    /**
     * Instantiates a new Enquery.
     *
     * @param id             the id
     * @param query          the query
     * @param answer         the answer
     * @param createStudent  the create student
     * @param suggestStudent the suggest student
     * @param answerStaff    the answer staff
     * @param campId         the camp id
     * @param suggestion     the suggestion
     * @param reply          the reply
     * @param isApprove      the is approve
     */
    public Enquery(String id, String query, String answer, String createStudent, String suggestStudent, String answerStaff, String campId, String suggestion, String reply, boolean isApprove) {
        this.id = id;
        this.query = query;
        this.answer = answer;
        this.createStudent = createStudent;
        this.suggestStudent = suggestStudent;
        this.answerStaff = answerStaff;
        this.campId = campId;
        this.suggestion = suggestion;
        this.reply = reply;
        this.isApprove = isApprove;
    }


    /**
     * Instantiates a new Enquery.
     *
     * @param query     the query
     * @param studentID the student id
     * @param campID    the camp id
     */
    public Enquery(String query, String studentID, String campID) {
        this.id = getRandomID();
        this.query = query;
        this.createStudent = studentID;
        this.campId = campID;
        this.answer = null;
        this.suggestStudent = null;
        this.answerStaff = null;
        this.suggestion = null;
        this.reply = null;

    }

    public Enquery(String suggestion, String studentID, String campID, String answerStaff,boolean isApprove) {
        this.id = getRandomID();
        this.query = null;
        this.createStudent = studentID;
        this.campId = campID;
        this.answer = null;
        this.suggestStudent = null;
        this.answerStaff = null;
        this.suggestion = suggestion;
        this.reply = null;
        this.isApprove = isApprove;
    }
    public Enquery(String suggestion, String suggestStudent, String campID,boolean isApprove) {
        this.id = getRandomID();
        this.query = null;
        this.createStudent = null;
        this.campId = campID;
        this.answer = null;
        this.suggestStudent = suggestStudent;
        this.answerStaff = null;
        this.suggestion = suggestion;
        this.reply = null;
        this.isApprove = isApprove;
    }


    public Enquery(String query, String reply, String studentID, String campId) {
        this.id = getRandomID();
        this.query = query;
        this.createStudent = studentID;
        this.campId = campId;
        this.answer = null;
        this.suggestStudent = null;
        this.answerStaff = null;
        this.suggestion = null;
        this.reply = reply;
    }


    @Override
    public String getID() {
        return id;
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


    /**
     * Gets reply.
     *
     * @return the reply
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets reply.
     *
     * @param reply the reply
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * Gets create student.
     *
     * @return the create student
     */
    public String getCreateStudent() {
        return createStudent;
    }

    /**
     * Sets create student.
     *
     * @param createStudent the create student
     */
    public void setCreateStudent(String createStudent) {
        this.createStudent = createStudent;
    }

    /**
     * Gets answer staff.
     *
     * @return the answer staff
     */
    public String getAnswerStaff() {
        return answerStaff;
    }

    /**
     * Sets answer staff.
     *
     * @param answerStaff the answer staff
     */
    public void setAnswerStaff(String answerStaff) {
        this.answerStaff = answerStaff;
    }

    /**
     * Gets answer.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets answer.
     *
     * @param answer the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets query.
     *
     * @param query the query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets camp id.
     *
     * @return the camp id
     */
    public String getCampId() {
        return campId;
    }

    /**
     * Sets camp id.
     *
     * @param campId the camp id
     */
    public void setCampId(String campId) {
        this.campId = campId;
    }

    /**
     * Gets suggestion.
     *
     * @return the suggestion
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * Sets suggestion.
     *
     * @param suggestion the suggestion
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * Gets is approve.
     *
     * @return the is approve
     */
    public boolean getIsApprove() {
        return isApprove;
    }

    /**
     * Sets is approve.
     *
     * @param approve the approve
     */
    public void setIsApprove(boolean approve) {
        isApprove = approve;
    }

    /**
     * Gets suggest student.
     *
     * @return the suggest student
     */
    public String getSuggestStudent() {
        return suggestStudent;
    }

    /**
     * Sets suggest student.
     *
     * @param suggestStudent the suggest student
     */
    public void setSuggestStudent(String suggestStudent) {
        this.suggestStudent = suggestStudent;
    }
}
