package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Data
@NoArgsConstructor
public class UserRegistrationRequest {

    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String repeatPassword;
}