package org.cegielka.periodicals.service.exception;

public class PublicationNotDeleteException extends RuntimeException {
    public PublicationNotDeleteException() {
        super("Publication not delete ");
    }
}