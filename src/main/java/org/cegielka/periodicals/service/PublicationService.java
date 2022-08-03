package org.cegielka.periodicals.service;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.repository.PublicationRepository;

import java.util.List;

public interface PublicationService {

    List<Publication> listAll();
}
