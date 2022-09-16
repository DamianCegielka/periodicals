package org.cegielka.periodicals.service.mapper;

import lombok.AllArgsConstructor;
import org.cegielka.periodicals.dto.UserResponse;
import org.cegielka.periodicals.entity.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserToResponseUserMapper {

    public UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPassword(user.getPassword());
        userResponse.setRepeatPassword(user.getPassword());
        return userResponse;
    }
}
