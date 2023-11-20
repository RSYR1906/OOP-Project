package org.sc2002.utils.exception;

public class RegistrationClosedException extends Exception {

    public RegistrationClosedException() {
        super("Registration is closed for this camp.");
    }

    public RegistrationClosedException(String message) {
        super(message);
    }

}
