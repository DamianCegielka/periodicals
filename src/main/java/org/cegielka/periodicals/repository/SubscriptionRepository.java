package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findSubscriptionsByPublicationAndUser(Publication publication, User user);

    List<Subscription> findSubscriptionsByUserId(Long id);

}
