package org.sc2002.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Staff extends User {

    public Staff(String name, String email, String password ,String faculty) {
        super(name, email, password,Faculty.valueOf(faculty.toUpperCase()));
    }
    //.
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
}
