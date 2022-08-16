package org.cegielka.periodicals.service.mapper;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.service.exception.SubscriptionNotAddException;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class SubscriptionRequestMapper {

    public static Subscription map(SubscriptionRequest request) {
        try {
            return new Subscription(request.getUserId(), request.getPublicationId(), request.getDate().truncatedTo(DAYS));
        } catch (Exception e) {
            throw new SubscriptionNotAddException();
        }
    }
}
