package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT s from Publication s WHERE s.title LIKE CONCAT('%',:query,'%')")
    List<Publication> searchAllPublicationByTitle(String query);

    @Query("select p from Publication p where p.title like concat('%', ?1, '%')")
    Page<Publication> findByTitleContaining(String title, Pageable pageable);

    @Query("select p from Publication p where p.accumulation.id = ?1")
    Page<Publication> findByGroup(Long groupValue, Pageable pageable);

    @Query("select p from Publication p where p.title like concat('%', ?1, '%') AND p.accumulation.id = ?2")
    Page<Publication> findByTitleContainingAndGroup(String title, Long groupValue, Pageable pageable);

}