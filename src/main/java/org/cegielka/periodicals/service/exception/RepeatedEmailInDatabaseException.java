package org.cegielka.periodicals.service.exception;

public class RepeatedEmailInDatabaseException extends RuntimeException {
    public RepeatedEmailInDatabaseException() {

        super("The email exists in the database. Please login or register for another email");
    }
}
