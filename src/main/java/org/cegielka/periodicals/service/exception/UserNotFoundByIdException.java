package org.cegielka.periodicals.service.exception;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException() {
        super("User Not Found");
    }
}