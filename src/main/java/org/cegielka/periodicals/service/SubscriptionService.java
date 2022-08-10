package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    boolean addSubscription(SubscriptionRequest request);

    boolean deleteSubscription(SubscriptionRequest subscriptionRequest);

    List<Subscription> showSubscriptionsSubscribingByUser(Long Userid);
}
