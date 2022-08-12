package org.cegielka.periodicals.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.repository.AccumulationRepository;
import org.cegielka.periodicals.repository.PublicationRepository;
import org.cegielka.periodicals.repository.SubscriptionRepository;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.PublicationNotAddEsception;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.mapper.PublicationRegistrationRequestMapper;
import org.cegielka.periodicals.service.validator.SubscriptionValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class PublicationServiceImpl implements PublicationService {
    PublicationRepository publicationRepository;
    AccumulationRepository accumulationRepository;
    SubscriptionRepository subscriptionRepository;
    SubscriptionValidator subscriptionValidator;
    UserService userService;


    @Override
    public List<Publication> listAll() {
        return publicationRepository.findAll();
    }

    @Override
    @Transactional
    public void register(PublicationRequest request) {
        try {
            Publication publication = PublicationRegistrationRequestMapper.map(request);
            publicationRepository.save(publication);
        } catch (PublicationNotAddEsception e) {
            e.printStackTrace();
        }
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
    public List<Publication> searchPublicationByTitle(String query) {
        if (query != null) {
            return publicationRepository.searchAllPublicationByTitle(query);
        } else {
            return publicationRepository.findAll();
        }
    }

    @Override
    public Page<Publication> findPaginatedWithSearching(int pageNo, int pageSize, String query, String sortField, String sortDirection, Long groupValue) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if (query != null && groupValue != null) {
            return publicationRepository.findByTitleContainingAndGroup(query, groupValue, pageable);
        } else if (groupValue != null) {
            return publicationRepository.findByGroup(groupValue, pageable);
        } else if (query != null) {
            return publicationRepository.findByTitleContaining(query, pageable);
        } else {
            return publicationRepository.findAll(pageable);
        }
    }

    @Override
    public List<Accumulation> getAllAccumulation() {
        return accumulationRepository.findAll();
    }
}
