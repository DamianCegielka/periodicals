package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.repository.AccumulationRepository;
import org.cegielka.periodicals.repository.PublicationRepository;
import org.cegielka.periodicals.service.impl.PublicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PublicationServiceTest {
    @Mock
    private PublicationRepository publicationRepository;
    @Mock
    private AccumulationRepository accumulationRepository;

    @Autowired
    private PublicationServiceImpl publServiceToTest;


    @BeforeEach
    void setUp() {
        publServiceToTest = new PublicationServiceImpl(
                publicationRepository, accumulationRepository);
    }

    @Test
    void shouldAddPublication() {

        PublicationRequest request = new PublicationRequest();
        request.setTitle("Example Title");
        request.setPrice(1l);
        request.setTopic("News");
        request.setAccumulation(new Accumulation());
        request.setDescription("Fresh News");

        when(publicationRepository.save(Mockito.any(Publication.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        Publication publication = publServiceToTest.add(request);

        assertNotNull(publication);
        assertEquals(request.getTitle(), publication.getTitle());
        assertEquals(request.getPrice(), publication.getPrice());
    }

}