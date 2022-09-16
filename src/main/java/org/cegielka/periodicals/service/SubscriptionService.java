package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.dto.SubscriptionResponse;
import org.cegielka.periodicals.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    boolean addSubscriptionByCurrentUser(Long idPublication);

    boolean deleteSubscriptionByCurrentUser(Long idPublication);

    List<SubscriptionResponse> getSubscriptionForUserId(Long userId);

    Boolean isInDatabase(SubscriptionRequest request);

    Boolean isActiveUser(Subscription subscription);

    boolean isUserHaveFoundsForSubscription(SubscriptionRequest request);
}
