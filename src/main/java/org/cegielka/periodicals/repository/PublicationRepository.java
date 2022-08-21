package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> searchAllByTitleContaining(String query);

    Page<Publication> findPublicationByTitleContains(String title, Pageable pageable);

    Page<Publication> findPublicationByAccumulationId(Long groupValue, Pageable pageable);

    Page<Publication> findPublicationByTitleContainsAndAccumulationId(String title, Long groupValue, Pageable pageable);

}