package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {

        String email = "damian@damian.com";
        String password = "periodicals";
        User user = new User();
                user.setEmail(email);
                user.setPassword(password);
        userRepository.save(user);

        Optional<User> expected = userRepository.findUserByEmail(user.getEmail());
        assertTrue(expected.isPresent());
        assertEquals( expected.get(),user);
    }

    @Test
    void shouldNotFindByEmail() {
        Long id = 1L;
        Optional<User> expected = userRepository.findById(id);
        assertFalse(expected.isPresent());
    }
}