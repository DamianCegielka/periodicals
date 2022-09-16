package org.cegielka.periodicals.service.mapper;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.Role;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.RoleRepository;
import org.cegielka.periodicals.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserRegistrationRequestMapper {

    RoleRepository roleRepository;
    UserRepository userRepository;

    public User map(UserRegistrationRequest requestMapper, String encodedPassword) {
        Optional<Role> role = roleRepository.findRoleByNameEquals("User");
        Role roleUser = null;
        if (role.isPresent()) {
            roleUser = role.get();
        } else {
            roleUser = new Role("User");
            roleRepository.save(roleUser);
        }
        User user = new User();
        if (requestMapper.getId() != null) {
            Optional<User> optionalUser = userRepository.findById(requestMapper.getId());
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                user.setEmail(requestMapper.getEmail());
            }
        } else {
            user = new User(requestMapper.getEmail(), encodedPassword, true, roleUser);
        }
        if (requestMapper.getFirstName() != null) {
            user.setFirstName(requestMapper.getFirstName());
        }
        if (requestMapper.getLastName() != null) {
            user.setLastName(requestMapper.getLastName());
        }
        return user;
    }
}