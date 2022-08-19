package org.cegielka.periodicals.service.exception;

public class PublicationNotDeleteException extends RuntimeException {
    public PublicationNotDeleteException() {
        super("Publication not delete. It is likely that some user subscribes to this item.");
    }
}