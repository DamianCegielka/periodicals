package org.cegielka.periodicals.service.exception;

public class IllegalPasswordValueException extends RuntimeException {
    public IllegalPasswordValueException() {

        super("Password has illegal format. Please enter a password that has at least 6 characters");
    }
}