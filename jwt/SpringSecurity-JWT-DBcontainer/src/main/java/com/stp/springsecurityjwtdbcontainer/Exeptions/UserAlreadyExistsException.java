package com.stp.springsecurityjwtdbcontainer.Exeptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

