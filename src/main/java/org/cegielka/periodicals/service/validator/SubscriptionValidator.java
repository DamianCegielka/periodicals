package org.cegielka.periodicals.service.validator;

import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.exception.SubscriptionNotExistInDatabaseException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubscriptionValidator {
    SubscriptionRepository subscriptionRepository;
    UserRepository userRepository;

    SubscriptionValidator(SubscriptionRepository subscriptionRepository,
                          UserRepository userRepository){
        this.subscriptionRepository=subscriptionRepository;
        this.userRepository=userRepository;
    }

    public Boolean isInDatabase(SubscriptionRequest request){
        Optional<Subscription> subscription=subscriptionRepository
                .findSubscriptionsByPublicationAndUser(request.getPublicationId(),request.getUserId());
       return subscription.isPresent();
    }

    public Boolean isActiveUser(Subscription subscription){
        Optional <User> user=userRepository.findById(subscription.getUser().getId());

       if(user.isPresent()) {
           return user.get().getActive();
       }
       else{
           return false;
       }
    }

    public Boolean isSubscriptionInDatabase(SubscriptionRequest request){
        Optional<Subscription> subscription=subscriptionRepository
                .findSubscriptionsByPublicationAndUser(request.getPublicationId(),request.getUserId());

        return subscription.isPresent();
    }


}
