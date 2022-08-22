package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
class SubscriptionRepositoryTest {

    Long idUserSubscriptionToTest;

    @Autowired
    private SubscriptionRepository subscriptionRepositoryTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private AccumulationRepository accumulationRepository;

    @Test
    void shouldFindUSubscriptionByPublicationAndUser() {

        String title = "TestName";
        Long price = 1L;
        String topic = "Test";
        Accumulation accumulation = new Accumulation();
        accumulation.setName("testName");
        accumulationRepository.save(accumulation);
        String description = "TestDescription";
        Publication publication = new Publication();
        publication.setTitle(title);
        publication.setPrice(price);
        publication.setTopic(topic);
        publication.setAccumulation(accumulation);
        publication.setDescription(description);
        publicationRepository.save(publication);

        String email = "damian@damian.test";
        String password = "periodicals";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        LocalDateTime date = LocalDateTime.now();

        Subscription subscription = new Subscription(user, publication, date);
        subscriptionRepositoryTest.save(subscription);
        Optional<Subscription> expected = subscriptionRepositoryTest.findSubscriptionsByPublicationAndUser(publication, user);
        assertTrue(expected.isPresent());
        assertEquals(expected.get().getPublication().getTitle(), title);
        assertEquals(expected.get().getUser().getEmail(), email);

        idUserSubscriptionToTest = expected.get().getUser().getId();
    }

    @Test
    void shouldNotFindSubscriptionById() {

        List<Subscription> expected = subscriptionRepositoryTest.findSubscriptionsByUserId(idUserSubscriptionToTest);
        assertTrue(expected.isEmpty());
    }
}