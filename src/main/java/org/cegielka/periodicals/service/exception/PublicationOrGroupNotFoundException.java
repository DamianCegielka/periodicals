package org.cegielka.periodicals.service.exception;

public class PublicationOrGroupNotFoundException extends RuntimeException {
    public PublicationOrGroupNotFoundException() {
        super(" Publication or Group not found");
    }
}