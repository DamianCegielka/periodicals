package org.cegielka.periodicals.service.validator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    private BCryptPasswordEncoder encoder;

    public PasswordValidator(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public String encodePasswordFromRegisterForm(String password) {

        return encoder.encode(password);
    }

    public boolean isMatched(String inputPassword, String encodedPassword) {
        return true;
    }
}
