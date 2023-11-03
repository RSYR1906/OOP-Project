package org.sc2002.utils.exception;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException() {
        super("Entity not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
