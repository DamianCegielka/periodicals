package org.cegielka.periodicals.service.validator;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SubscriptionValidator {
    SubscriptionRepository subscriptionRepository;
    UserRepository userRepository;

    public Boolean isInDatabase(SubscriptionRequest request) {
        Optional<Subscription> subscription = subscriptionRepository
                .findSubscriptionsByPublicationAndUser(request.getPublicationId(), request.getUserId());
        return subscription.isPresent();
    }

    public Boolean isActiveUser(Subscription subscription) {
        Optional<User> user = userRepository.findById(subscription.getUser().getId());
        if (user.isPresent()) {
            return user.get().getActive();
        } else {
            return false;
        }
    }

    public boolean isUserHaveFoundsForSubscription(SubscriptionRequest request) {
        return (request.getUserId().getAccount() >= request.getPublicationId().getPrice());
    }
}
