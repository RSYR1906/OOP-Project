package org.sc2002.utils.exception;

public class CampConflictException extends Exception {

    public CampConflictException() {
        super("The camp has time conflict.");
    }

    public CampConflictException(String message) {
        super(message);
    }
}
