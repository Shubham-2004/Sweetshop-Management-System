package com.sweetshop.sweetshop_backend.controller;

import com.sweetshop.sweetshop_backend.service.SweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;

@WebMvcTest(SweetController.class)
public class SweetDeleteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SweetService sweetService;

    @Test
    @WithMockUser
    void testDeleteSweetSuccess() throws Exception {
        String sweetId = "123";
        when(sweetService.deleteSweet(sweetId)).thenReturn(true);
        mockMvc.perform(delete("/api/sweets/{id}", sweetId)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}