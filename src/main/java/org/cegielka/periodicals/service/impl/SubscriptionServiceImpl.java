package org.cegielka.periodicals.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.PublicationRepository;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.SubscriptionService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.SubscriptionNotAddException;
import org.cegielka.periodicals.service.exception.SubscriptionNotDeleteException;
import org.cegielka.periodicals.service.mapper.SubscriptionRequestMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final PublicationRepository publicationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final PublicationService publicationService;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public boolean addSubscriptionByCurrentUser(Long idPublication) {
        try {
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setPublicationId(publicationService.get(idPublication));
            subscriptionRequest.setUserId(userService.get());
            subscriptionRequest.setDate(LocalDateTime.now());
            Subscription subscription = SubscriptionRequestMapper.map(subscriptionRequest);
            if (this.isInDatabase(subscriptionRequest)
                    || (!this.isActiveUser(subscription))
                    || (!this.isUserHaveFoundsForSubscription(subscriptionRequest))) {
                return false;
            } else {
                userService.calculateFoundsOnAccountUser(subscriptionRequest.getUserId().getId(),
                        subscriptionRequest.getPublicationId().getPrice().intValue());
                subscriptionRepository.save(subscription);
                return true;
            }
        } catch (Exception e) {
            throw new SubscriptionNotAddException();
        }
    }

    @Override
    public boolean deleteSubscriptionByCurrentUser(Long idPublication) {
        try {
            SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
            subscriptionRequest.setPublicationId(publicationService.get(idPublication));
            subscriptionRequest.setUserId(userService.get());
            Optional<Subscription> subscription = subscriptionRepository.findSubscriptionsByPublicationAndUser
                    (subscriptionRequest.getPublicationId(), subscriptionRequest.getUserId());
            if (subscription.isPresent()) {
                subscriptionRepository.delete(subscription.get());
                return true;
            } else {
                return false;
            }
        }
        catch(Exception e){
            throw new SubscriptionNotDeleteException();
        }
    }

    @Override
    public List<Subscription> getSubscriptionForUserId(Long userid) {
        return subscriptionRepository.findSubscriptionsByUserId(userid);
    }

    @Override
    public Boolean isInDatabase(SubscriptionRequest request) {
        Optional<Subscription> subscription = subscriptionRepository
                .findSubscriptionsByPublicationAndUser(request.getPublicationId(), request.getUserId());
        return subscription.isPresent();
    }

    @Override
    public Boolean isActiveUser(Subscription subscription) {
        Optional<User> user = userRepository.findById(subscription.getUser().getId());
        if (user.isPresent()) {
            return user.get().getActive();
        } else {
            return false;
        }
    }
    @Override
    public boolean isUserHaveFoundsForSubscription(SubscriptionRequest request) {
        return (request.getUserId().getAccount() >= request.getPublicationId().getPrice());
    }
}
