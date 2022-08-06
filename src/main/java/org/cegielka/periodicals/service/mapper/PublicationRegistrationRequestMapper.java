package org.cegielka.periodicals.service.mapper;

import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.entity.Publication;
import org.springframework.stereotype.Component;

@Component
public class PublicationRegistrationRequestMapper {
    public static Publication map(PublicationRegistrationRequest request) {

        Publication publication = new Publication();

        publication.setId(request.getId());
        publication.setTitle(request.getTitle());
        publication.setPrice(request.getPrice());
        publication.setTopic(request.getTopic());

        return publication;
    }
}