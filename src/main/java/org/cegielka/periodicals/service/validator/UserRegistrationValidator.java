package org.cegielka.periodicals.service.validator;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.service.exception.IllegalEmailValueException;
import org.cegielka.periodicals.service.exception.IllegalPasswordValueException;
import org.cegielka.periodicals.service.exception.PasswordsAreNotSameException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserRegistrationValidator {
    static public final int MIN_VALID_PASSWORD_LENGTH = 6;
    public void validate(UserRegistrationRequest request) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            throw new IllegalEmailValueException();
        }
        String password = request.getPassword();
        String repeatPassword = request.getRepeatPassword();

        if (!password.equals(repeatPassword)) {
            throw new PasswordsAreNotSameException();
        }
        if (password == null || password.length() < MIN_VALID_PASSWORD_LENGTH) {
            throw new IllegalPasswordValueException();
        }
    }
}


