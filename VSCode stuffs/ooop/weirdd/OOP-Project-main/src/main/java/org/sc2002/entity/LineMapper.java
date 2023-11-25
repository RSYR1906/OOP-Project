package org.sc2002.entity;

/**
 * The interface Line mapper.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface LineMapper<T> {
    /**
     * Map line t.
     *
     * @param fields the fields
     * @return the t
     */
    T mapLine(String[] fields);
}
