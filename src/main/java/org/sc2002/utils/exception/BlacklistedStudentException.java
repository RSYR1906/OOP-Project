package org.sc2002.utils.exception;

public class BlacklistedStudentException extends Exception {

    public BlacklistedStudentException() {
        super("Student has already withdrawn from this camp before. Cannot register again");
    }

    public BlacklistedStudentException(String message) {
        super(message);
    }
}