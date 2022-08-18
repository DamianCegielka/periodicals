package org.cegielka.periodicals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cegielka.periodicals.entity.Accumulation;
import org.cegielka.periodicals.entity.Publication;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationAndGroupResponse {

    private Publication publication;
    private List<Accumulation> listGroup;

}
