package org.cegielka.periodicals.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.repository.PublicationRepository;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.service.SubscriptionService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.SubscriptionNotAddException;
import org.cegielka.periodicals.service.mapper.SubscriptionRequestMapper;
import org.cegielka.periodicals.service.validator.SubscriptionValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    PublicationRepository publicationRepository;
    SubscriptionRepository subscriptionRepository;
    SubscriptionValidator subscriptionValidator;
    UserService userService;

    @Override
    @Transactional
    public boolean addSubscription(SubscriptionRequest request) {
        try {
            Subscription subscription = SubscriptionRequestMapper.map(request);
            if (subscriptionValidator.isInDatabase(request)
                    || (!subscriptionValidator.isActiveUser(subscription))
                    || (!subscriptionValidator.isUserHaveFoundsForSubscription(request))) {
                return false;
            } else {
                userService.calculateFoundsOnAccountUser(request.getUserId().getId(),
                        request.getPublicationId().getPrice().intValue());
                subscriptionRepository.save(subscription);
                return true;
            }
        } catch (SubscriptionNotAddException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSubscription(SubscriptionRequest request) {
        Optional<Subscription> subscription = subscriptionRepository.findSubscriptionsByPublicationAndUser
                (request.getPublicationId(), request.getUserId());
        boolean result;
        if (result = subscription.isPresent()) {
            subscriptionRepository.delete(subscription.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Subscription> getSubscriptionForUserId(Long userid) {
        return subscriptionRepository.findSubscriptionsByUserId(userid);
    }
}
