package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Accumulation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class AccumulationRepositoryTest {

    @Autowired
    private AccumulationRepository accumulationRepositoryTest;

    @Test
    void shouldFindAccumulation() {

        String name = "News from Last Year";
        Accumulation accumulation = new Accumulation();
        accumulation.getId();
        accumulation.setName(name);
        accumulationRepositoryTest.save(accumulation);
        List<Accumulation> expected = accumulationRepositoryTest.findAll();
        assertTrue(expected.stream().anyMatch(acc->acc==accumulation));
    }

    @Test
    void shouldNotFindAccumulation() {
        String name = "Economy";
        Accumulation accumulation = new Accumulation();
        accumulation.getId();
        accumulation.setName(name);
        List<Accumulation> expected = accumulationRepositoryTest.findAll();
        assertFalse(expected.stream().anyMatch(acc->acc==accumulation));
    }
}
