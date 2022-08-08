package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.OptionalValueBinding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Optional<Publication> findAllPublicationByTitle(String Title);
    @Query("SELECT s from Publication s WHERE s.title LIKE CONCAT('%',:query,'%')")
    List<Publication> searchAllPublicationByTitle(String query);

    @Query("select p from Publication p where p.title like concat('%', ?1, '%')")
    Page<Publication> findByTitleContaining(String title, Pageable pageable);

}