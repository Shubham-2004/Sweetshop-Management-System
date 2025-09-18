package com.sweetshop.sweetshop_backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.sweetshop_backend.model.User;
import com.sweetshop.sweetshop_backend.service.AuthService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureWebMvc
public class AuthSignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testUserSignupSuccess() throws Exception {
        User newUser = new User("newuser", "password123");
        when(authService.registerUser("newuser", "password123"))
                .thenReturn("User registered successfully");

        mockMvc.perform(post("/api/auth/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));
    }

    
    @Test
    @WithMockUser
    void testUserSignupFailure_UserAlreadyExists() throws Exception {
        User existingUser = new User("existinguser", "password123");
        when(authService.registerUser("existinguser", "password123"))
                .thenReturn("Username already exists");

        mockMvc.perform(post("/api/auth/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingUser)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username already exists"));
    }


    @Test
    @WithMockUser
    void testUserSignupFailure_EmptyUsername() throws Exception {
        User userWithEmptyUsername = new User("", "password123");
        when(authService.registerUser("", "password123"))
                .thenReturn("Username cannot be empty");

        mockMvc.perform(post("/api/auth/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithEmptyUsername)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Username cannot be empty"));
    }

    
}