package org.sc2002.utils.exception;

/**
 * The type Camp full exception.
 */
public class CampFullException extends Exception {

    /**
     * Instantiates a new Camp full exception.
     */
    public CampFullException() {
        super("The camp is full.");
    }

    /**
     * Instantiates a new Camp full exception.
     *
     * @param message the message
     */
    public CampFullException(String message) {
        super(message);
    }
}
