package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepositoryTest;

    @AfterEach
    void tearDown() {
        roleRepositoryTest.deleteAll();
    }

    @Test
    void shouldFindRoleByName() {

        String name = "Editor";
        Role role = new Role();
        role.getId();
        role.setName(name);
        roleRepositoryTest.save(role);
        Optional<Role> expected = roleRepositoryTest.findRoleByNameEquals(name);
        assertTrue(expected.isPresent());
        assertTrue(expected.stream().anyMatch(exp -> exp == role));
    }

    @Test
    void shouldNotFindRoleByName() {
        String name = "Editor";
        Role role = new Role();
        role.getId();
        role.setName(name);
        Optional<Role> expected = roleRepositoryTest.findRoleByNameEquals(name);
        assertFalse(expected.isPresent());
        assertFalse(expected.stream().anyMatch(exp -> exp == role));
    }
}