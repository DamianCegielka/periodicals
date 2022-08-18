package org.cegielka.periodicals.service.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("User not login");
    }
}