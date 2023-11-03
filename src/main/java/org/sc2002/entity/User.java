package org.sc2002.entity;

public abstract class User implements Entity{

    private String name;
    private String email;
    private Faculty faculty;

    public User(String name, String email,  Faculty faculty) {
        this.email = email;
        this.name = name;
        this.faculty = faculty;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Faculty getFaculty() {
        return faculty;
    }


    public abstract String getID();
}
