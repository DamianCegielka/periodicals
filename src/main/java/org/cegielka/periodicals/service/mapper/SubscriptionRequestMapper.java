package org.cegielka.periodicals.service.mapper;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionRequestMapper {

    public static Subscription map(SubscriptionRequest request) {
        return new Subscription(request.getUserId(), request.getPublicationId(), request.getDate());
    }
}
