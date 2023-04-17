package com.stp.springsecurityjwtdbcontainer.Exeptions;

public class UserIsNotAdminException extends Throwable {
    public UserIsNotAdminException(String message) {
        super(message);
    }
}
