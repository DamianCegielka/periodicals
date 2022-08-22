package org.cegielka.periodicals.service;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.RoleRepository;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.exception.IllegalEmailValueException;
import org.cegielka.periodicals.service.exception.PasswordsAreNotSameException;
import org.cegielka.periodicals.service.impl.UserServiceImpl;
import org.cegielka.periodicals.service.mapper.UserLoggedMapper;
import org.cegielka.periodicals.service.mapper.UserRegistrationRequestMapper;
import org.cegielka.periodicals.service.validator.UserRegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Rollback
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserRegistrationRequestMapper userRegistrationRequestMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserLoggedMapper userLoggedMapper;
    @Spy
    private UserRegistrationValidator userRegistrationValidator;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl underTest;


    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository,
                userRegistrationRequestMapper,
                userLoggedMapper,
                userRegistrationValidator,
                passwordEncoder,
                roleRepository);
    }

    @Test
    void shouldFindUserByEmail() {
        User expectedUser = new User();
        expectedUser.setEmail("test@test.com");
        expectedUser.setPassword(passwordEncoder.encode("testTest"));
        userRepository.save(expectedUser);
        when(userRepository.findUserByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.of(expectedUser));
        User actual = (User) underTest.loadUserByUsername(expectedUser.getEmail());
        assertEquals(expectedUser, actual);
        Mockito.verify(userRepository, times(1)).save(expectedUser);
        Mockito.verify(userRepository, times(1)).findUserByEmail(expectedUser.getEmail());
        Mockito.verify(passwordEncoder, times(1)).encode("testTest");

    }

    @Test
    void shouldRegisterUser() {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("create21@test.com");
        request.setPassword("testTest");
        request.setRepeatPassword("testTest");
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        User user = underTest.register(request);
        assertNotNull(user);
        assertEquals(request.getEmail(), user.getEmail());
        assertNotEquals(passwordEncoder.encode(request.getPassword()), user.getPassword());
    }

    @Test
    void shouldNotRegisterUserByWrongEmail() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("create@");
        request.setPassword("testTest");
        request.setRepeatPassword("testTest");
        assertThrows(IllegalEmailValueException.class, () -> underTest.register(request));
    }

    @Test
    void shouldNotRegisterUserByDifferentPasswords() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("create2@test.com");
        request.setPassword("testTest1");
        request.setRepeatPassword("testTest2");
        assertThrows(PasswordsAreNotSameException.class, () -> underTest.register(request));
    }

    @Test
    void shouldFindAllUser() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("create31@test.com");
        request.setPassword("testTest");
        request.setRepeatPassword("testTest");
        when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        User user = underTest.register(request);
        assertNotNull(user);
        assertNotEquals(1, underTest.listAll().size());

    }
}
