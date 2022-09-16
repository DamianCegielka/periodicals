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
    private static final int MIN_VALID_PASSWORD_LENGTH = 8;

    public void validate(UserRegistrationRequest request) {
        String regexEmail = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(request.getEmail());
        if (!matcher.matches()) {
            throw new IllegalEmailValueException();
        }
        if (request.getId() == null) {
            String password = request.getPassword();
            String repeatPassword = request.getRepeatPassword();
            String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
            Pattern patternPassword = Pattern.compile(regexPassword);
            Matcher matcherPassword = patternPassword.matcher(request.getPassword());

            if (!matcherPassword.matches() || password == null || password.length() < MIN_VALID_PASSWORD_LENGTH) {
                throw new IllegalPasswordValueException();
            } else if (!password.equals(repeatPassword)) {
                throw new PasswordsAreNotSameException();
            }
        }
    }
}


