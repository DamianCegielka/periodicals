package org.cegielka.periodicals.service.validator;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    public String encode(String password){
        return password;
    };
    public boolean isMatched(String password, String encodedPassword){
        return true;
    };
}
