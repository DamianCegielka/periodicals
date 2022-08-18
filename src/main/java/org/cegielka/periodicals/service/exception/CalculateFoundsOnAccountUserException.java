package org.cegielka.periodicals.service.exception;

public class CalculateFoundsOnAccountUserException extends RuntimeException {
    public CalculateFoundsOnAccountUserException() {
        super("Money from the account not taken");
    }
}
