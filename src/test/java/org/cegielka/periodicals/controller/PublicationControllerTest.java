package org.cegielka.periodicals.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PublicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusOkFormNewFormPublication()throws Exception{
        mockMvc.perform(get("/publications/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}