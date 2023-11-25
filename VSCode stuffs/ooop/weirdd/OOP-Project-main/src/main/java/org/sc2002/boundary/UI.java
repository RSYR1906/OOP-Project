package org.sc2002.boundary;

import org.sc2002.utils.exception.*;

/**
 * The interface Ui.
 *
 * @param <T> the type parameter
 */
public interface UI<T> {
    /**
     * Body t.
     *
     * @return the t
     * @throws CampFullException           the camp full exception
     * @throws RegistrationClosedException the registration closed exception
     * @throws EntityNotFoundException     the entity not found exception
     * @throws FacultyNotEligibleException the faculty not eligible exception
     * @throws CampConflictException       the camp conflict exception
     */
    public T body() throws CampFullException, RegistrationClosedException, EntityNotFoundException, FacultyNotEligibleException, CampConflictException;
}
