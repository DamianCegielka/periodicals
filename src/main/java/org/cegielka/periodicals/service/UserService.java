package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(UserRegistrationRequest user);

    User login(String email, String password);

    List<User> listAll();

    public User get();

    void reverseState(Long id);

    void calculateFoundsOnAccountUser(Long userId, int price);

    LoggedUserIdAndRoleResponse getLoggerUser();

    public String encodePasswordFromRegisterForm(String password);

    public boolean isMatched(String inputPassword, String encodedPassword);
}
