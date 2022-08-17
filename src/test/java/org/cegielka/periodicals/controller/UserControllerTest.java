package org.cegielka.periodicals.controller;

import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void status302WhenTryGetAccessToUserListByNoLoginAdmin() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void status302WhenTryGetAccessToSubscriptionListByNoLoginUser() throws Exception {
        this.mockMvc.perform(get("/users/subscription"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }


    @Test
    public void allUsersWhenAdminIsLogIn() throws Exception {
        User adminUser = userRepository.findUserByEmail("damian@damian").get();
        var user = userService.loadUserByUsername(adminUser.getUsername());
        this.mockMvc.perform(get("/users")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("listUsers"))
                .andExpect(model().attributeExists("userRole"))
                .andExpect(model().attributeExists("idLoginUser"));
    }

    @Test
    public void allSubscriptionsForUserWhenLogIn() throws Exception {
        User adminUser = userRepository.findUserByEmail("cegielka@cegielka").get();
        var user = userService.loadUserByUsername(adminUser.getUsername());
        this.mockMvc.perform(get("/users/subscription")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("user_subscription"))
                .andExpect(model().attributeExists("listPublications"))
                .andExpect(model().attributeExists("userRole"))
                .andExpect(model().attributeExists("idLoginUser"));
    }

    @Test
    public void personalProfileWhenUserIsLogIn() throws Exception {
        User adminUser = userRepository.findUserByEmail("cegielka@cegielka").get();
        var user = userService.loadUserByUsername(adminUser.getUsername());
        this.mockMvc.perform(get("/users//profile")
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("user_profile"))
                .andExpect(model().attributeExists("listUsers"))
                .andExpect(model().attributeExists("userRole"))
                .andExpect(model().attributeExists("idLoginUser"));
    }
}
