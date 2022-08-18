package org.cegielka.periodicals.service.exception;

public class IllegalEmailValueException extends RuntimeException {
    public IllegalEmailValueException() {
        super("Email format is illegal");
    }
}
