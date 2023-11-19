package org.sc2002.entity;

public abstract class User implements Entity{

    private String name;
    private String email;
    private Faculty faculty;

    private String password;

    public User(String name, String email, String password, Faculty faculty) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.faculty = faculty;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Faculty getFaculty() {
        return faculty;
    }
    
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public abstract String getID();
}
