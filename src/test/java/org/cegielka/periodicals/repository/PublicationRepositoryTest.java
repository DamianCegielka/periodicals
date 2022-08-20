package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class PublicationRepositoryTest {


    @Autowired
    private PublicationRepository publicationRepositoryTest;
    @Autowired
    private AccumulationRepository accumulationRepositoryTest;
    @AfterEach
    void tearDown(){
        publicationRepositoryTest.deleteAll();
    }

    @Test
    void shouldFindPublicationByTitle() {

        String title = "TestName";
        Long price = 1L;
        String topic = "Test";
        Accumulation accumulation = new Accumulation();
        accumulation.setName("testName");
        accumulationRepositoryTest.save(accumulation);
        String description = "TestDescription";
        Publication publication = new Publication();
        publication.setTitle(title);
        publication.setPrice(price);
        publication.setTopic(topic);
        publication.setAccumulation(accumulation);
        publication.setDescription(description);
        publicationRepositoryTest.save(publication);
        List<Publication> expected = publicationRepositoryTest.searchAllByTitleContaining("TestName");
        assertEquals(expected.stream().anyMatch(obj->obj.getTitle()=="TestName"),true);

    }

    @Test
    void shouldNotFindPublicationByTitle() {
        List<Publication> expected = publicationRepositoryTest.searchAllByTitleContaining("TestName");
        assertFalse(expected.stream().anyMatch(obj->obj.getTitle()=="TestName"));
    }
}
