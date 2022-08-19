package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByNameEquals(String user);

}
