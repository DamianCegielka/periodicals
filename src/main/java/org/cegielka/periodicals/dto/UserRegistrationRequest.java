package org.cegielka.periodicals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    @Email(message = "Please provide valid Email")
    private String email;

    @ToString.Exclude
    @Size(min = 6)
    private String password;

    @ToString.Exclude
    @Size(min = 6)
    private String repeatPassword;

}