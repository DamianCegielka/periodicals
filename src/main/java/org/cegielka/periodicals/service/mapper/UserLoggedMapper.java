package org.cegielka.periodicals.service.mapper;


import org.cegielka.periodicals.dto.LoggedUserIdAndRoleResponse;
import org.cegielka.periodicals.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserLoggedMapper {

    public LoggedUserIdAndRoleResponse mapToLoggedUser(User customUser){
        return new LoggedUserIdAndRoleResponse(customUser.getId(), customUser.getRole().getRole());
    }

    public LoggedUserIdAndRoleResponse mapToNotLoggedUser(){
        return new LoggedUserIdAndRoleResponse();
    }
}
