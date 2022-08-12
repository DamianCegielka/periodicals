package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@ToString
@Data
@NoArgsConstructor
public class UserRegistrationRequest {

    @NotBlank(message="CANT BE EMPTY!")
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String repeatPassword;
}