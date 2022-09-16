package org.cegielka.periodicals.service.exception;

public class PasswordsAreNotSameException extends RuntimeException {
    public PasswordsAreNotSameException() {

        super("Passwords are not the same.The password and repeated password should be the same.");
    }
}