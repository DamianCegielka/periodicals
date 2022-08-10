package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.User;

import java.time.LocalDateTime;

@ToString
@Data
@NoArgsConstructor
public class SubscriptionRequest {

    private User userId;
    private Publication publicationId;
    LocalDateTime date;
}