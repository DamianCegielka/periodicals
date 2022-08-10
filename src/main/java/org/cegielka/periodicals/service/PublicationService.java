package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.dto.SubscriptionRequest;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.Subscription;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PublicationService {

    List<Publication> listAll();

    void register(PublicationRequest request);

    Publication get(Long id);

    void delete(Long id);

    boolean addSubscription(SubscriptionRequest request);

    List<Publication> searchPublicationByTitle(String query);

    boolean deleteSubscription(SubscriptionRequest subscriptionRequest);

    Page<Publication> findPaginatedWithSearching(int pageNo,
                                                 int pageSize,
                                                 String query,
                                                 String sortField,
                                                 String sortDirection,
                                                 Long groupValue);

    List<Subscription> showPublicationsSubscribingByUser(Long Userid);

}