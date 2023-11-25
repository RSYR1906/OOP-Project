package org.sc2002.entity;

public class CampStudent implements Entity{

    private Camp camp;
    private Student student;
    private CampRole campRole;
    private Boolean hasWithdrawn;

    public CampStudent( Camp camp,Student student,CampRole campRole, Boolean hasWithdrawn) {
        this.camp = camp;
        this.student = student;
        this.campRole = campRole;
        this.hasWithdrawn = hasWithdrawn;

    }

    public Student getStudent() {
        return student;
    }

    public Camp getCamp() {
        return camp;
    }

    public Boolean getHasWithdrawn() {
        return hasWithdrawn;
    }

    public CampRole getCampRole() {
        return campRole;
    }

    @Override
    public String getID() {
        return camp.getID()+"-"+student.getID();
    }
}
