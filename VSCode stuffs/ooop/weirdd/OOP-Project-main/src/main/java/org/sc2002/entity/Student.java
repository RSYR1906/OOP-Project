package org.sc2002.entity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

import org.sc2002.utils.exception.CampFullException;
import org.sc2002.utils.exception.EntityNotFoundException;

/**
 * The type Student.
 */
public class Student extends User {

    private String CampCommitteeMember;// is no use
    private List<Camp> registeredCamps;
    private int score;


    /**
     * Instantiates a new Student.
     *
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @param faculty  the faculty
     */
    public Student(String name, String email, String password, String faculty, int score) {
        super(name, email, password, Faculty.valueOf(faculty.toUpperCase()));
        this.registeredCamps = new ArrayList<>();
        this.CampCommitteeMember = "";
        this.score = score;
    }

    public Student(String name, String email, String password, String faculty) {
        super(name, email, password, Faculty.valueOf(faculty.toUpperCase()));
        this.registeredCamps = new ArrayList<>();
        this.CampCommitteeMember = "";
    }

    @Override
    public String getID() {
        Pattern pattern = Pattern.compile("^(.*?)@");
        Matcher matcher = pattern.matcher(super.getEmail());
        if (matcher.find()) {
            // Group 1 contains the user ID
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

//   // public boolean isCampCommitteeMember() {
//        return isCampCommitteeMember;
//    }

    /**
     * Gets camp committee member.
     *
     * @return the camp committee member
     */
    public String getCampCommitteeMember() {
        return CampCommitteeMember;
    }

    /**
     * Sets camp committee member.
     *
     * @param campCommitteeMember the camp committee member
     */
    public void setCampCommitteeMember(String campCommitteeMember) {
        CampCommitteeMember = campCommitteeMember;
    }

    /**
     * Register committee.
     *
     * @param camp the camp
     */
    public void registerCommittee(Camp camp) {

    }

    /**
     * Register for camp.
     *
     * @param camp the camp
     */
    public void registerForCamp(Camp camp) {
        if (!registeredCamps.contains(camp)) {
            try {
                camp.registerStudent(this);
                registeredCamps.add(camp);
            } catch (CampFullException e) {

            }
        }
    }

    /**
     * Withdraw from camp.
     *
     * @param camp the camp
     */
    public void withdrawFromCamp(Camp camp) {
        if (registeredCamps.contains(camp)) {
            try {
                camp.withdrawStudent(this);
                registeredCamps.remove(camp);
            } catch (EntityNotFoundException e) {

            }
        }
    }


    /**
     * Gets registered camps.
     *
     * @return the registered camps
     */
    public List<Camp> getRegisteredCamps() {
        return new ArrayList<>(registeredCamps);
    }
}

