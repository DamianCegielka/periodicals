package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.repository.AccumulationRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PublicationService {

    List<Publication> listAll();

    void register(PublicationRequest request);

    Publication get(Long id);

    void delete(Long id);

    List<Publication> searchPublicationByTitle(String query);

    Page<Publication> findPaginatedWithSearching(int pageNo, int pageSize,
                                                 String query, String sortField,
                                                 String sortDirection, Long groupValue);
    List<Accumulation> getAllAccumulation();
}