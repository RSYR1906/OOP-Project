package org.sc2002.utils.exception;

public class DuplicateEntityExistsException extends Exception{

    public DuplicateEntityExistsException() {
        super("Entity with same ID already exists!");
    }

    public DuplicateEntityExistsException(String message) {
        super(message);
    }
}
