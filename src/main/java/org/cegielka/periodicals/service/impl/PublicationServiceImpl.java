package org.cegielka.periodicals.service.impl;

import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.repository.PublicationRepository;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.exception.SubscriptionNotAddException;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.mapper.PublicationRegistrationRequestMapper;
import org.cegielka.periodicals.service.mapper.SubscriptionRequestMapper;
import org.cegielka.periodicals.service.validator.SubscriptionValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class PublicationServiceImpl implements PublicationService {
    PublicationRepository publicationRepository;
    SubscriptionRepository subscriptionRepository;
    SubscriptionValidator subscriptionValidator;


    PublicationServiceImpl(PublicationRepository publicationRepository,
                           SubscriptionRepository subscriptionRepository,
                           SubscriptionValidator subscriptionValidator) {
        this.publicationRepository = publicationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionValidator = subscriptionValidator;
    }

    @Override
    public List<Publication> listAll() {
        return (List<Publication>) publicationRepository.findAll();
    }

    @Override
    public void register(PublicationRegistrationRequest request) {
        Publication publication = PublicationRegistrationRequestMapper.map(request);
        publicationRepository.save(publication);
    }

    @Override
    public Publication get(Long id) {
        Optional<Publication> resultById;
        resultById = publicationRepository.findById(id);
        if (resultById.isPresent()) {
            return resultById.get();
        } else {
            throw new UserNotFoundByIdException();
        }
    }

    @Override
    public void delete(Long id) {
        publicationRepository.deleteById(id);
    }

    @Override
    public boolean addSubscription(SubscriptionRequest request) {
        if (subscriptionValidator.isRepeatedInDatabase(request)) {
            return false;
        } else {
            Subscription subscription = SubscriptionRequestMapper.map(request);
            subscriptionRepository.save(subscription);
            return true;
        }
    }

    @Override
    public List<Publication> searchPublicationByTitle(String query) {
        List<Publication> publications= publicationRepository.searchAllPublicationByTitle(query);
        return publications;
    }
}