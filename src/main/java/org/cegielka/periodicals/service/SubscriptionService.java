package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    boolean addSubscriptionByCurrentUser(Long idPublication);

    boolean deleteSubscriptionByCurrentUser(Long idPublication);

    List<Subscription> getSubscriptionForUserId(Long Userid);

    Boolean isInDatabase(SubscriptionRequest request);

    public Boolean isActiveUser(Subscription subscription);

    public boolean isUserHaveFoundsForSubscription(SubscriptionRequest request);
}
