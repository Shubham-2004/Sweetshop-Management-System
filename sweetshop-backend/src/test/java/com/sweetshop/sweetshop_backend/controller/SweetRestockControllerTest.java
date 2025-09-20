package com.sweetshop.sweetshop_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.sweetshop_backend.service.SweetService;
import com.sweetshop.sweetshop_backend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebMvcTest(SweetController.class)
public class SweetRestockControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testRestockSweetSuccess() throws Exception {
        String sweetId = "123";
        int quantity = 50;
        String token = "valid-admin-token";
        
        when(jwtUtil.extractUsername(token)).thenReturn("kashyap");
        when(jwtUtil.extractRole(token)).thenReturn("ADMIN");
        when(jwtUtil.validateToken(token, "kashyap")).thenReturn(true);
        when(sweetService.restockSweet(sweetId, quantity)).thenReturn(true);

        Map<String, Object> request = new HashMap<>();
        request.put("quantity", quantity);

        mockMvc.perform(post("/api/sweets/{id}/restock", sweetId)
                .header("Authorization", "Bearer " + token)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Restock successful"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.sweetId").value("123"))
                .andExpect(jsonPath("$.restockedQuantity").value(50));
    }
}