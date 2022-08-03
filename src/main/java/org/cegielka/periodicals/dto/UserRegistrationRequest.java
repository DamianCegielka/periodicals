package org.cegielka.periodicals.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Data
public class UserRegistrationRequest {
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String repeatPassword;
    public UserRegistrationRequest(){

    }

    public UserRegistrationRequest(String email, String password, String repeatPassword) {
        super();
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
