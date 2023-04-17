package com.stp.springsecurityjwtdbcontainer.Exeptions;

public class UserDoesntExistException extends Exception{

    public UserDoesntExistException(String message) {
        super(message);
    }
}
