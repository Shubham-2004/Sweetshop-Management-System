package com.sweetshop.sweetshop_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.sweetshop_backend.model.Sweet;
import com.sweetshop.sweetshop_backend.service.SweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;

@WebMvcTest(SweetController.class)
public class SweetUpdateTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testUpdateSweetSuccess() throws Exception {
        // Arrange
        Sweet updatedSweet = new Sweet("Updated Chocolate Bar", "Candy", 3.00, 150);
        updatedSweet.setId("123");

        when(sweetService.updateSweet(eq("123"), any(Sweet.class))).thenReturn(updatedSweet);

        Sweet requestSweet = new Sweet("Updated Chocolate Bar", "Candy", 3.00, 150);

        // Act & Assert
        mockMvc.perform(put("/api/sweets/123")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestSweet)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Updated Chocolate Bar"))
                .andExpect(jsonPath("$.category").value("Candy"))
                .andExpect(jsonPath("$.price").value(3.00))
                .andExpect(jsonPath("$.quantity").value(150));
    }

    @Test
    @WithMockUser
    void testUpdateSweetNotFound() throws Exception {
        // Arrange
        Sweet requestSweet = new Sweet("Non-existent Sweet", "Test", 1.00, 10);
        
        when(sweetService.updateSweet(eq("999"), any(Sweet.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/sweets/999")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestSweet)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}