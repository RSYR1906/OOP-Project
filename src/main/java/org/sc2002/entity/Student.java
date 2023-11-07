package org.sc2002.entity;

import java.util.ArrayList;
import java.util.List;
import org.sc2002.utils.exception.CampFullException;
import org.sc2002.utils.exception.EntityNotFoundException;

public class Student extends User {

    private boolean isCampCommitteeMember;
    private List<Camp> registeredCamps;

    public Student(String name, String email, String password, String faculty) {
        super(name, email, password,Faculty.valueOf(faculty.toUpperCase()));
        this.isCampCommitteeMember = false;
        this.registeredCamps = new ArrayList<>();
    }

    @Override
    public String getID() {
        return super.getEmail();
    }
    
    public boolean isCampCommitteeMember() {
        return isCampCommitteeMember;
    }

    public void setCampCommitteeMember(boolean campCommitteeMember) {
        isCampCommitteeMember = campCommitteeMember;
    }

    public void registerForCamp(Camp camp) {
        if (!registeredCamps.contains(camp)) {
            try {
                camp.registerStudent(this);
                registeredCamps.add(camp);
            } catch (CampFullException e) {
            	
            }
        }
    }

    public void withdrawFromCamp(Camp camp) {
        if (registeredCamps.contains(camp)) {
            try {
                camp.withdrawStudent(this);
                registeredCamps.remove(camp);
            } catch (EntityNotFoundException e) {
               
            }
        }
    }


    public List<Camp> getRegisteredCamps() {
        return new ArrayList<>(registeredCamps);
    }
}

