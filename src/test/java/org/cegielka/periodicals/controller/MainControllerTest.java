package org.cegielka.periodicals.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void statusOkForHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void statusOkForLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void statusOkForLogoutPage() throws Exception {
        mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void notLoginUserWhenUseInvalidEmail() throws Exception {

        this.mockMvc.perform(post("/login").param("@cegielka", "cegielka789"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void notLoginUserWhenUseInvalidpassword() throws Exception {

        this.mockMvc.perform(post("/login").param("cegielka@cegielka", "cegielka765"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void foundStatusWhenUserPutProperData() throws Exception {
        this.mockMvc.perform(formLogin().user("damian@damian").password("damian456"))
                .andDo(print())
                .andExpect(status().isFound());
    }
}
