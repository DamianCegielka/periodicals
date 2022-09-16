package org.cegielka.periodicals.service.exception;

public class IllegalPasswordValueException extends RuntimeException {
    public IllegalPasswordValueException() {

        super("""
                Password has illegal format.Password must contain at least:
                one lowercase Latin character [a-z],
                one uppercase Latin character [A-Z],
                one special character like ! @ # & ( ).
                one number [0-9],
                length of at least 8 characters""");
    }
}