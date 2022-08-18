package org.cegielka.periodicals.service.exception;

public class IllegalPasswordValueException extends RuntimeException {
    public IllegalPasswordValueException() {
        super("Password has illegal format");
    }
}