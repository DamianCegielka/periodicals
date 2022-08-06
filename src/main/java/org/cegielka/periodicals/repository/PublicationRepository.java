package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.entity.Publication;
import org.springframework.data.querydsl.binding.OptionalValueBinding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicationRepository extends CrudRepository<Publication, Long> {
    Optional<Publication> findAllPublicationByTitle(String Title);

}