package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.Subscription;
import org.cegielka.periodicals.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.publication = ?1 and s.user = ?2")
    Optional<Subscription> findSubscriptionsByPublicationAndUser(Publication publication, User user);

    @Query("select s from Subscription s where s.user.id = ?1")
    List<Subscription> findSubscriptionsByUserId(Long id);

}
