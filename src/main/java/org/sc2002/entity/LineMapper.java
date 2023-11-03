package org.sc2002.entity;

@FunctionalInterface
public interface LineMapper<T> {
    T mapLine(String[] fields);
}
