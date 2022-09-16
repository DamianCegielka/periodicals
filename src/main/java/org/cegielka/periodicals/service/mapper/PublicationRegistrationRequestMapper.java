package org.cegielka.periodicals.service.mapper;

import org.cegielka.periodicals.dto.PublicationRequest;
import org.cegielka.periodicals.entity.Publication;
import org.springframework.stereotype.Component;

@Component
public class PublicationRegistrationRequestMapper {

    private PublicationRegistrationRequestMapper() {
    }

    public static Publication map(PublicationRequest request) {

        Publication publication = new Publication();
        publication.setId(request.getId());
        publication.setTitle(request.getTitle());
        publication.setPrice(request.getPrice());
        publication.setTopic(request.getTopic());
        publication.setAccumulation(request.getAccumulation());
        publication.setDescription(request.getDescription());
        return publication;
    }


}