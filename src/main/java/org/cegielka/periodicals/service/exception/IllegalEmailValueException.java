package org.cegielka.periodicals.service.exception;

public class IllegalEmailValueException extends RuntimeException {
    public IllegalEmailValueException() {

        super("Email format is illegal. Please provide email in correct format , eg.: example@email.com");
    }
}
