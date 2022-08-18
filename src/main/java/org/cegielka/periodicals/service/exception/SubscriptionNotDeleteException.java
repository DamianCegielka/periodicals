package org.cegielka.periodicals.service.exception;

public class SubscriptionNotDeleteException extends RuntimeException {
    public SubscriptionNotDeleteException() {
        super("Subscription not removed for unknown reason ");
    }
}