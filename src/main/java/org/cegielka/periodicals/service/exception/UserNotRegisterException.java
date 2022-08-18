package org.cegielka.periodicals.service.exception;

public class UserNotRegisterException extends RuntimeException {
    public UserNotRegisterException() {
        super("User not register");
    }
}
