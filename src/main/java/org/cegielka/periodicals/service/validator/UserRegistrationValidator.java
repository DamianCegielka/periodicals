package org.cegielka.periodicals.service.validator;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.service.exception.IllegalPaswordValueException;
import org.cegielka.periodicals.service.exception.PasswordsAreNotSameException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationValidator {
    static public final int MIN_VALID_PASSWORD_LENGTH =6;

    public void validate(UserRegistrationRequest request){
        String password = request.getPassword();
        String repeatPassword = request.getRepeatPassword();
        if (!password.equals(repeatPassword)) {//TODO NPE?
            throw new PasswordsAreNotSameException();
        }
        if(password==null || password.length()< MIN_VALID_PASSWORD_LENGTH){
            throw new IllegalPaswordValueException();
        }

        //email as login validate
    }
}


