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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;

@WebMvcTest(SweetController.class)
public class SweetAddTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testCreateSweetSuccess() throws Exception {
        Sweet savedSweet = new Sweet("Chocolate Bar", "Candy", 2.50, 100);
        savedSweet.setId("123");

        when(sweetService.createSweet(any(Sweet.class))).thenReturn(savedSweet);

        Sweet newSweet = new Sweet("Chocolate Bar", "Candy", 2.50, 100);

        mockMvc.perform(post("/api/sweets")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSweet)))
                .andDo(print()) // This will show the response in console
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void testCreateSweetSuccess2() throws Exception {
        Sweet savedSweet = new Sweet("Rasgulla", "Dessert", 2.50, 100);
        savedSweet.setId("124");

        when(sweetService.createSweet(any(Sweet.class))).thenReturn(savedSweet);

        Sweet newSweet = new Sweet("Rasgulla", "Dessert", 2.50, 100);

        mockMvc.perform(post("/api/sweets")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSweet)))
                .andDo(print()) // This will show the response in console
                .andExpect(status().isCreated());
    }
}