package org.cegielka.periodicals.service.exception;

public class SubscriptionNotAddException extends RuntimeException {
    public SubscriptionNotAddException(){
        super("Subscription not add for unknown reason");
    }
}