package org.sc2002.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Staff.
 */
public class Staff extends User {

    private List<Camp> createdCamps;

    /**
     * Instantiates a new Staff.
     *
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @param faculty  the faculty
     */
    public Staff(String name, String email, String password ,String faculty) {
        super(name, email, password,Faculty.valueOf(faculty.toUpperCase()));
        this.createdCamps = new ArrayList<>();
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
     * Gets created camps.
     *
     * @return the created camps
     */
    public List<Camp> getCreatedCamps() {
        return createdCamps;
    }

    /**
     * Add to created camps.
     *
     * @param newCamp the new camp
     */
    public void addToCreatedCamps(Camp newCamp) {
        this.createdCamps.add(newCamp); // need to filter out duplicate camps
    }

    /**
     * Delete from created camps.
     *
     * @param campToDelete the camp to delete
     */
    public void deleteFromCreatedCamps(Camp campToDelete){
        this.createdCamps.remove(campToDelete); // need to filter out if camp exists
    }
}
