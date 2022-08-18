package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cegielka.periodicals.entity.Accumulation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@ToString
@Data
@NoArgsConstructor
public class PublicationRequest {

    private Long id;
    @NotBlank
    private String title;
    @Min(value = 1, message = "To must be greater than zero")
    private Long price;
    @NotBlank
    private String topic;
    private Accumulation accumulation;
    private String description;
}