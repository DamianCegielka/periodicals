package org.cegielka.periodicals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cegielka.periodicals.entity.Publication;
import org.cegielka.periodicals.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {

    private Long id;
    private User user;
    private Publication publication;
    private String date;
}
