package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;

import java.util.List;

public interface RegistrationService {
    void register(UserRegistrationRequest user);

    User login(String email, String password);

    List<User> listAll();

    void delete(Long id);

    public User get(Long id);

    void updateState(Long id);
}
