package org.cegielka.periodicals.service.mapper;

import org.cegielka.periodicals.dto.PublicationRegistrationRequest;
import org.cegielka.periodicals.entity.Publication;

public class PublicationRegistrationRequestMapper {
    public static Publication map(PublicationRegistrationRequest request) {
        return new Publication(request.getTitle(),request.getPrice(),request.getTopic());
    }
}
