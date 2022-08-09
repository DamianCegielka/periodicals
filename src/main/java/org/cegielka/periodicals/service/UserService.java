package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(UserRegistrationRequest user);

    User login(String email, String password);

    List<User> listAll();

    void delete(Long id);

    public User get(Long id);

    void updateState(Long id);

    Long getIdUserWhichIsLogin();

    void calculateFoundsOnAccountUser(Long userId,int price);
}
