package org.cegielka.periodicals.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.PaginationResponse;
import org.cegielka.periodicals.dto.PublicationAndGroupResponse;
import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.repository.AccumulationRepository;
import org.cegielka.periodicals.repository.PublicationRepository;
import org.cegielka.periodicals.service.PublicationService;
import org.cegielka.periodicals.service.exception.*;
import org.cegielka.periodicals.service.mapper.PublicationRegistrationRequestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class PublicationServiceImpl implements PublicationService {
    private final PublicationRepository publicationRepository;
    private final AccumulationRepository accumulationRepository;

    @Override
    public void add(PublicationRequest request) {
        try {
            Publication publication = PublicationRegistrationRequestMapper.map(request);
            publicationRepository.save(publication);
        } catch (Exception e) {
            throw new PublicationNotAddException();
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
        try {
            publicationRepository.deleteById(id);
        } catch (Exception e) {
            throw new PublicationNotDeleteException();
        }
    }

    @Override
    public List<Publication> searchPublicationByTitle(String query) {
        if (query != null) {
            return publicationRepository.searchAllByTitleContaining(query);
        } else {
            return publicationRepository.findAll();
        }
    }

    @Override
    public Page<Publication> findPaginatedWithSearching(PaginationResponse request) {
        try {
            Sort sort = request.getSortDirection().equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(request.getSortField()).ascending()
                    : Sort.by(request.getSortField()).descending();
            Pageable pageable = PageRequest.of(request.getPageNo() - 1, request.getPageSize(), sort);
            if (request.getQuery() != null && request.getGroupValue() != null) {
                return publicationRepository.findPublicationByTitleContainsAndAccumulation_Id(request.getQuery(), request.getGroupValue(), pageable);
            } else if (request.getGroupValue() != null) {
                return publicationRepository.findPublicationByAccumulation_Id(request.getGroupValue(), pageable);
            } else if (request.getQuery() != null) {
                return publicationRepository.findPublicationByTitleContains(request.getQuery(), pageable);
            } else {
                return publicationRepository.findAll(pageable);
            }
        } catch (Exception e) {
            throw new PaginationNotExecuteException();
        }
    }

    @Override
    public List<Accumulation> getAllAccumulation() {

        return accumulationRepository.findAll();
    }

    @Override
    public PublicationAndGroupResponse getPublicationAndAccumulation(Long idPublication) {
        try {
            PublicationAndGroupResponse response = new PublicationAndGroupResponse();
            response.setPublication(this.get(idPublication));
            response.setListGroup(this.getAllAccumulation());
            return response;
        } catch (Exception e) {
            throw new PublicationOrGroupNotFoundException();
        }
    }
}
