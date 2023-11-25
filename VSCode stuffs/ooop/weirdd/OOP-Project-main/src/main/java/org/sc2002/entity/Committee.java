package org.sc2002.entity;

import org.sc2002.utils.RandomIDGenerator;

import java.util.Random;

/**
 * The type Committee.
 *
 * @ClassName: Commitee

 * @Create: 2023 -11-23 16:42
 */
public class Committee implements Entity {
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
     * Instantiates a new Committee.
     */
    public Committee() {
        this.id = RandomIDGenerator.getRandomID();
    }

    /**
     * Instantiates a new Committee.
     *
     * @param campId    the camp id
     * @param studentid the studentid
     */
    public Committee(String campId, String studentid) {
        this.id = RandomIDGenerator.getRandomID();
        this.campId = campId;
        this.studentid = studentid;

    }

    /**
     * Instantiates a new Committee.
     *
     * @param id        the id
     * @param campId    the camp id
     * @param studentId the student id
     */
    public Committee(String id,String campId, String studentId) {
        this.id = id;
        this.campId = campId;
        this.studentid = studentId;

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



    @Override
    public String getID() {
        return this.id;
    }
}
