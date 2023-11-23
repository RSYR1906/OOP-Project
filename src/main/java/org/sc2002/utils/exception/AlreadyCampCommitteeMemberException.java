package org.sc2002.utils.exception;

public class AlreadyCampCommitteeMemberException extends Exception {

    public AlreadyCampCommitteeMemberException() {
        super("Student is already a camp committee member of a camp");
    }

    public AlreadyCampCommitteeMemberException(String message) {
        super(message);
    }
}
