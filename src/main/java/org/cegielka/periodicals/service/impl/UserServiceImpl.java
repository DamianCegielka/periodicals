package org.cegielka.periodicals.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.CalculateFoundsOnAccountUserException;
import org.cegielka.periodicals.service.exception.LoginException;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.exception.UserNotRegisterException;
import org.cegielka.periodicals.service.mapper.UserLoggedMapper;
import org.cegielka.periodicals.service.mapper.UserRegistrationRequestMapper;
import org.cegielka.periodicals.service.validator.UserRegistrationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRegistrationRequestMapper userRegistrationRequestMapper;
    private final UserLoggedMapper userLoggedMapper;
    private final UserRegistrationValidator userRegistrationValidator;
    private final BCryptPasswordEncoder encoder;


    @Override
    @Transactional
    public void register(UserRegistrationRequest request) {
            userRegistrationValidator.validate(request);
            String encodePassword = this.encodePasswordFromRegisterForm(request.getPassword());
            User user = userRegistrationRequestMapper.map(request, encodePassword);
            userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(RuntimeException::new);
        boolean matched = this.isMatched(password, user.getPassword());
        if (!matched) {
            throw new LoginException();
        }
        return user;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User get() {
        Optional<User> resultById = userRepository.findById(this.getLoggerUser().getId());
        if (resultById.isPresent()) {
            return resultById.get();
        } else {
            throw new UserNotFoundByIdException();
        }
    }

    @Override
    public void reverseState(Long id) {
        Optional<User> resultById;
        resultById = userRepository.findById(id);
        if (resultById.isPresent()) {
            User user = resultById.get();
            Boolean active = user.getActive();
            user.setActive(!active);
            userRepository.save(user);
        } else {
            throw new UserNotFoundByIdException();
        }
    }

    @Override
    public LoggedUserIdAndRoleResponse getLoggerUser() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() == "anonymousUser")
                return userLoggedMapper.mapToNotLoggedUser();
            User customUser = (User) authentication.getPrincipal();
            return userLoggedMapper.mapToLoggedUser(customUser);
    }

    @Override
    @Transactional
    public void calculateFoundsOnAccountUser(Long userId, int price) {
        try {
            int actualFunds = (userRepository.findById(userId).get().getAccount()) - price;
            userRepository.findById(userId).get().setAccount(actualFunds);
        } catch (Exception e) {
            throw new CalculateFoundsOnAccountUserException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public String encodePasswordFromRegisterForm(String password) {

        return encoder.encode(password);
    }

    public boolean isMatched(String inputPassword, String encodedPassword) {
        return (encodedPassword.equals(this.encodePasswordFromRegisterForm(inputPassword)));
    }
}





