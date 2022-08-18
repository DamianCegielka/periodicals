package org.cegielka.periodicals.service.exception;

public class PaginationNotExecuteException extends RuntimeException {
    public PaginationNotExecuteException() {
        super("Pagination Not Execute");
    }
}