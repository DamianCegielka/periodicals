package org.cegielka.periodicals.repository;

import org.cegielka.periodicals.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("select r from Role r where r.role ='User' ")
    Optional<Role> findRoleByNameEqualUser();
}
