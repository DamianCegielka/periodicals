package org.cegielka.periodicals.service.validator;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.service.exception.SubscriptionExistInDatabaseException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubscriptionValidator {
    SubscriptionRepository subscriptionRepository;

    SubscriptionValidator(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository=subscriptionRepository;
    }

    public Boolean isRepeatedInDatabase(SubscriptionRequest request){
        Optional<Subscription> subscription=subscriptionRepository
                .findSubscriptionsByPublicationAndUser(request.getPublicationId(),request.getUserId());
       return subscription.isPresent();
    }
}
