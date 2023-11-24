package org.sc2002.utils.exception;

public class WrongStaffException extends Exception {

    public WrongStaffException() {
        super("Wrong Staff for the camp");
    }

    public WrongStaffException(String message) {
        super(message);
    }
}
