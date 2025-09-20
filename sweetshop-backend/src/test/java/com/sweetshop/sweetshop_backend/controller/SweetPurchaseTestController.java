package com.sweetshop.sweetshop_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.sweetshop_backend.service.SweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;

@WebMvcTest(SweetController.class)
public class SweetPurchaseTestController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testPurchaseSweetSuccess() throws Exception {
        String sweetId = "123";
        int quantity = 5;
        
        when(sweetService.purchaseSweet(sweetId, quantity)).thenReturn(true);

        String requestBody = objectMapper.writeValueAsString(new PurchaseRequest(sweetId, quantity));

        mockMvc.perform(post("/api/sweets/purchase")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Purchase successful"));
    }

    static class PurchaseRequest {
        private String sweetId;
        private int quantity;

        public PurchaseRequest(String sweetId, int quantity) {
            this.sweetId = sweetId;
            this.quantity = quantity;
        }

        public String getSweetId() {
            return sweetId;
        }

        public void setSweetId(String sweetId) {
            this.sweetId = sweetId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}