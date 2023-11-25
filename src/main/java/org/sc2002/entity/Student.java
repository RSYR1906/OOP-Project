package org.sc2002.entity;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import org.sc2002.utils.exception.CampFullException;
import org.sc2002.utils.exception.EntityNotFoundException;

public class Student extends User {

    private boolean isCampCommitteeMember;

    private Camp committeeMemberCamp;
    private List<Camp> registeredCamps;

    private int point;

    public Student(String name, String email, String password, String faculty) {
        super(name, email, password,Faculty.valueOf(faculty.toUpperCase()));
        this.isCampCommitteeMember = false;
        this.registeredCamps = new ArrayList<>();
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
    
    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    public void setCampCommitteeMember(boolean campCommitteeMember) {
        isCampCommitteeMember = campCommitteeMember;
    }

    public void registerForCamp(Camp camp) {
        registeredCamps.add(camp);
    }

    public void registerForCampAsCampCommitteeMember(Camp camp){
        registeredCamps.add(camp);
        committeeMemberCamp = camp;
        isCampCommitteeMember = true;
    }

    public void withdrawFromCamp(Camp camp) {
        registeredCamps.remove(camp);
    }


    public ArrayList<Camp> getRegisteredCamps() {
        return new ArrayList<>(registeredCamps);
    }

    public Camp getCommitteeMemberCamp() {
        return committeeMemberCamp;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

