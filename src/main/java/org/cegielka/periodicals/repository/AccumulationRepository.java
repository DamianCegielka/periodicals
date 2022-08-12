package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Accumulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccumulationRepository extends JpaRepository<Accumulation,Long> {

    List<Accumulation> findAll();
}
