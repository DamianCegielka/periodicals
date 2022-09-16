package org.cegielka.periodicals.service.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("Role Not Found");
    }
}
