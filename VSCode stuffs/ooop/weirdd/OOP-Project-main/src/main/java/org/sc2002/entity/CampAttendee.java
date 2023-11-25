package org.sc2002.entity;

import org.sc2002.utils.RandomIDGenerator;

/**
 * The type Camp attendee.
 *
 * @ClassName: CampAttendee

 * @Create: 2023 -11-23 16:42
 */
public class CampAttendee implements Entity {
    /**
     * The Id.
     */
    String id;
    /**
     * The Camp id.
     */
    String campId;
    /**
     * The Studentid.
     */
    String studentid;
    /**
     * The Forbid.
     */
    boolean forbid = false;

    /**
     * Instantiates a new Camp attendee.
     */
    public CampAttendee() {
        this.id = RandomIDGenerator.getRandomID();
    }

    /**
     * Instantiates a new Camp attendee.
     *
     * @param campId    the camp id
     * @param studentid the studentid
     */
    public CampAttendee(String campId, String studentid) {
        this.id = RandomIDGenerator.getRandomID();
        this.campId = campId;
        this.studentid = studentid;
    }

    /**
     * Instantiates a new Camp attendee.
     *
     * @param id        the id
     * @param campId    the camp id
     * @param studentId the student id
     * @param forbid    the forbid
     */
    public CampAttendee(String id, String campId, String studentId, boolean forbid) {
        this.id = id;
        this.campId = campId;
        this.studentid = studentId;
        this.forbid = forbid;
    }

    /**
     * Instantiates a new Camp attendee.
     *
     * @param campId    the camp id
     * @param studentId the student id
     * @param forbid    the forbid
     */
    public CampAttendee( String campId, String studentId, boolean forbid) {
        this.campId = campId;
        this.studentid = studentId;
        this.forbid = forbid;
    }

    @Override
    public String getID() {
        return id;
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
     * Gets studentid.
     *
     * @return the studentid
     */
    public String getStudentid() {
        return studentid;
    }

    /**
     * Sets studentid.
     *
     * @param studentid the studentid
     */
    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    /**
     * Is forbid boolean.
     *
     * @return the boolean
     */
    public boolean isForbid() {
        return forbid;
    }

    /**
     * Sets forbid.
     *
     * @param forbid the forbid
     */
    public void setForbid(boolean forbid) {
        this.forbid = forbid;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setID(String id) {
        this.id = id;
    }
}
