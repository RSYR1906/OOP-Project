package org.sc2002.utils.exception;

/**
 * The type Entity not found exception.
 */
public class EntityNotFoundException extends Exception{

    /**
     * Instantiates a new Entity not found exception.
     */
    public EntityNotFoundException() {
        super("Entity not found");
    }

    /**
     * Instantiates a new Entity not found exception.
     *
     * @param message the message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
