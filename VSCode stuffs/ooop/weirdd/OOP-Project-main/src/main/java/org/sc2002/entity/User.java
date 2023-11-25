package org.sc2002.entity;

/**
 * The type User.
 */
public abstract class User implements Entity{

    private String name;
    private String email;
    private Faculty faculty;

    private String password;

    /**
     * Instantiates a new User.
     *
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @param faculty  the faculty
     */
    public User(String name, String email, String password, Faculty faculty) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.faculty = faculty;

    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets faculty.
     *
     * @return the faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }


    public abstract String getID();

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
