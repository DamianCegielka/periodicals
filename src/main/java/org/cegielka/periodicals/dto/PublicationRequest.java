package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Data
@NoArgsConstructor
public class PublicationRequest {

    private Long id;
    private String title;
    private Long price;
    private String topic;
}