package org.cegielka.periodicals.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.UserService;
import org.cegielka.periodicals.service.exception.CalculateFoundsOnAccountUserException;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.mapper.UserRegistrationRequestMapper;
import org.cegielka.periodicals.service.validator.PasswordValidator;
import org.cegielka.periodicals.service.validator.UserRegistrationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserRegistrationRequestMapper userRegistrationRequestMapper;
    private PasswordValidator passwordValidator;
    private UserRegistrationValidator userRegistrationValidator;

    @Override
    @Transactional
    public void register(UserRegistrationRequest request) {
        userRegistrationValidator.validate(request);
        String encodePassword = passwordValidator.encodePasswordFromRegisterForm(request.getPassword());
        User user = userRegistrationRequestMapper.map(request, encodePassword);
        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException());
        boolean matched = passwordValidator.isMatched(password, user.getPassword());

        if (!matched) {
            throw new RuntimeException();
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id) throws UserNotFoundByIdException {
        Optional<User> resultById = userRepository.findById(id);
        if (resultById.isPresent()) {
            return resultById.get();
        } else {
            throw new UserNotFoundByIdException();
        }
    }

    @Override
    public void updateState(Long id) {
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
    public Long getIdUserWhichIsLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") return null;
        User customUser = (User) authentication.getPrincipal();
        return customUser.getId();
    }

    @Override
    public String getUserRoleWhichIsLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") return null;
        User customUser = (User) authentication.getPrincipal();
        return customUser.getRole().getRole();
    }

    @Override
    @Transactional
    public void calculateFoundsOnAccountUser(Long userId, int price) {
        try {
            int actualFunds = (userRepository.findById(userId).get().getAccount()) - price;
            userRepository.findById(userId).get().setAccount(actualFunds);
        } catch (CalculateFoundsOnAccountUserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}





