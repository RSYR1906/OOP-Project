package org.sc2002.entity;

public class Staff extends User {

    public Staff(String name, String email,  String faculty) {
        super(name, email, Faculty.valueOf(faculty.toUpperCase()));
    }

    @Override
    public String getID() {
        return super.getEmail();
    }
}
