package org.sc2002.utils.exception;

/**
 * The type Camp conflict exception.
 *
 */
public class CampConflictException  extends Exception {
    /**
     * Instantiates a new Camp conflict exception.
     */
    public CampConflictException() {
        super("The camp is conflict.");
    }

    /**
     * Instantiates a new Camp conflict exception.
     *
     * @param message the message
     */
    public CampConflictException(String message) {
        super(message);
    }
}
