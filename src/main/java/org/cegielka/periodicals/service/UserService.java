package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.dto.UserResponse;
import org.cegielka.periodicals.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User register(UserRegistrationRequest user);

    User login(String email, String password);

    List<User> listAll();

    User get();

    UserResponse getUserDataToEdit();

    void reverseState(Long id);

    void calculateFoundsOnAccountUser(Long userId, int price);

    LoggedUserIdAndRoleResponse getLoggerUser();

    String encodePasswordFromRegisterForm(String password);

    boolean isMatched(String inputPassword, String encodedPassword);
}
