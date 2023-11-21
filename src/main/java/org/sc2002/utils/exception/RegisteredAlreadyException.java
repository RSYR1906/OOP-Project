package org.sc2002.utils.exception;

public class RegisteredAlreadyException extends Exception {

    public RegisteredAlreadyException() {
        super("Student is registered already!");
    }

    public RegisteredAlreadyException(String message) {
        super(message);
    }
}