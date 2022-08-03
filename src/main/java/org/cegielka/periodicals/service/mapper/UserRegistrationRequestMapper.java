package org.cegielka.periodicals.service.mapper;

import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.Role;
import org.cegielka.periodicals.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import java.util.*;
import org.springframework.stereotype.Component;


@Component
public class UserRegistrationRequestMapper {
    public User map(UserRegistrationRequest requestMapper,String encodedPassword){
        return new User(requestMapper.getEmail(),encodedPassword,true);
    }
}
