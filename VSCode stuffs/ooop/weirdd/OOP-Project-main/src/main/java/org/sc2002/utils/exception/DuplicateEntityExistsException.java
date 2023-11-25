package org.sc2002.utils.exception;

/**
 * The type Duplicate entity exists exception.
 */
public class DuplicateEntityExistsException extends Exception{

    /**
     * Instantiates a new Duplicate entity exists exception.
     */
    public DuplicateEntityExistsException() {
        super("Entity with same ID already exists!");
    }

    /**
     * Instantiates a new Duplicate entity exists exception.
     *
     * @param message the message
     */
    public DuplicateEntityExistsException(String message) {
        super(message);
    }
}
