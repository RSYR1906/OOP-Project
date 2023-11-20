package org.sc2002.utils.exception;

public class CampFullException extends Exception {

    public CampFullException() {
        super("The camp is full and cannot accept more registrations.");
    }

    public CampFullException(String message) {
        super(message);
    }
}
