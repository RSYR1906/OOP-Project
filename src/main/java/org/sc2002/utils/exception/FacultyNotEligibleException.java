package org.sc2002.utils.exception;

public class FacultyNotEligibleException extends Exception {

    public FacultyNotEligibleException() {
        super("Student's faculty is not eligible for this camp.");
    }

    public FacultyNotEligibleException(String message) {
        super(message);
    }

}
