package com.sweetshop.sweetshop_backend.controller;

import com.sweetshop.sweetshop_backend.model.Sweet;
import com.sweetshop.sweetshop_backend.service.SweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(SweetController.class)
public class SweetSearchControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @Test
    @WithMockUser
    void testSearchSweetsByName() throws Exception {
        // Arrange
        List<Sweet> chocolateSweets = Arrays.asList(
            new Sweet("Dark Chocolate", "", 0, 0),
            new Sweet("Milk Chocolate", "", 0, 0)
        );

        when(sweetService.searchSweetsByName("chocolate")).thenReturn(chocolateSweets);

        // Act & Assert
        mockMvc.perform(get("/api/sweets/search")
                .param("name", "chocolate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dark Chocolate"))
                .andExpect(jsonPath("$[1].name").value("Milk Chocolate"));
    }
}