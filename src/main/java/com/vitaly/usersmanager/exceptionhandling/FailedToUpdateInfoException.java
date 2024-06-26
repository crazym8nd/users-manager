package com.vitaly.usersmanager.exceptionhandling;

public class FailedToUpdateInfoException extends RuntimeException {
    public FailedToUpdateInfoException(String message) {
        super(message);
    }
}
