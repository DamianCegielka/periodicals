package org.cegielka.periodicals.service.mapper;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.Role;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.RoleRepository;
import org.cegielka.periodicals.service.exception.RoleNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserRegistrationRequestMapper {

    RoleRepository roleRepository;

    public User map(UserRegistrationRequest requestMapper, String encodedPassword) {
        Optional<Role> role = roleRepository.findRoleByNameEquals("User");
        Role roleUser = null;
        if (role.isPresent()) roleUser = role.get();
        else throw new RoleNotFoundException();
        return new User(requestMapper.getEmail(), encodedPassword, true, roleUser);
    }
}